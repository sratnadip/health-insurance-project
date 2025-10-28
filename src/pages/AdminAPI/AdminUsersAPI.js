import axios from "axios";

const BASE_URL = "http://localhost:8089/api/policies";

export const getAllBoughtPolicies = async (token) => {
  const res = await axios.get(`${BASE_URL}/buy/all`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  return res.data;
};
