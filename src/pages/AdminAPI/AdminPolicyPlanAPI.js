import axios from "axios";

const API_URL = "http://localhost:8089/admin";

export const createPolicyPlan = async (adminId, planData) => {
  return await axios.post(`${API_URL}/${adminId}/policy-plans`, planData);
};

export const getPlansByAdmin = async (adminId) => {
  return await axios.get(`${API_URL}/${adminId}/policy-plans`);
};
