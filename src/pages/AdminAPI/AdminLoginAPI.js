import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "http://localhost:8089",
  headers: { "Content-Type": "application/json" },
});

axiosInstance.interceptors.request.use(
  (config) => {
    if (
      !config.url.includes("/api/admin/login") &&
      !config.url.includes("/api/admin/verify-otp")
    ) {
      const token = sessionStorage.getItem("adminToken");
      if (token) config.headers["Authorization"] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export const login = async (email) => {
  const res = await axiosInstance.post("/api/admin/login", { email });
  return res.data;
};

export const verifyOtp = async (email, otp) => {
  const res = await axiosInstance.post("/api/admin/verify-otp", { email, otp });
  return res.data;
};

export default axiosInstance;
