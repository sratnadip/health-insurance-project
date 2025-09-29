import axios from "axios";

const API_BASE_URL = "http://localhost:8089/api/v1/admin";

export const saveAdminProfile = async (data) => {
  try {
    const res = await axios.post(`${API_BASE_URL}/save`, data, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    return res.data;
  } catch (err) {
    console.error("API Error:", err.response || err);
    throw err;
  }
};
