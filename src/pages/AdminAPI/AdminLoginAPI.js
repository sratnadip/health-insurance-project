import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "http://localhost:8089", // backend base URL
  headers: {
    "Content-Type": "application/json",
  },
});

// Add token to headers for protected routes
axiosInstance.interceptors.request.use(
  (config) => {
    if (
      !config.url.includes("/api/admin/register") &&
      !config.url.includes("/api/admin/login") &&
      !config.url.includes("/api/admin/verify-otp")
    ) {
      const adminToken = sessionStorage.getItem("adminToken"); // 🔹 use sessionStorage
      if (adminToken) config.headers["Authorization"] = `Bearer ${adminToken}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Step 1: login and send OTP
export const login = async (email, password) => {
  const res = await axiosInstance.post("/api/admin/login", { email, password });

  // Store token if SUPER_ADMIN (skip OTP)
  if (res.data?.token && res.data?.role === "SUPER_ADMIN") {
    sessionStorage.setItem("adminToken", res.data.token); // 🔹 sessionStorage
    sessionStorage.setItem("adminData", JSON.stringify(res.data)); // 🔹 sessionStorage
  }

  return res.data;
};

// Step 2: verify OTP
export const verifyOtp = async (email, otp, password) => {
  const res = await axiosInstance.post("/api/admin/verify-otp", { email, otp, password });

  if (res.data?.token) {
    sessionStorage.setItem("adminToken", res.data.token); // 🔹 sessionStorage
    sessionStorage.setItem("adminData", JSON.stringify(res.data)); // 🔹 sessionStorage
  }

  return res.data;
};

export default axiosInstance;
