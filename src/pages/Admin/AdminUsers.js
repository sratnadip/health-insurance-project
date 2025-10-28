import React, { useEffect, useState } from "react";
import axios from "axios";
import "../Admin/AdminUsers.css";
import "../AdminAPI/AdminUsersAPI.js"

export default function AdminViewAllBoughtPolicies() {
  const [policies, setPolicies] = useState([]);
  const [loading, setLoading] = useState(true);

  // Fetch all bought policies
  useEffect(() => {
    const fetchPolicies = async () => {
      try {
        const token = localStorage.getItem("token"); 
        const res = await axios.get("http://localhost:8089/api/policies/buy/all", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setPolicies(res.data);
      } catch (error) {
        console.error("Error fetching policies:", error);
      } finally {
        setLoading(false);
      }
    };   
    fetchPolicies();
  }, []);

  if (loading) return <p className="loading">Loading policies...</p>;

  return (
    <div className="admin-policy-container">
      <h2>All Purchased Policies</h2>
      {policies.length === 0 ? (
        <p>No policies have been purchased yet.</p>
      ) : (
        <table className="policy-table">
          <thead>
            <tr>
              <th>Policy ID</th>
              <th>Policy Name</th>
              <th>Type</th>
              <th>Coverage</th>
              <th>Premium</th>
              <th>Duration (Years)</th>
              <th>Buyer Name</th>
              <th>Buyer Email</th>
            </tr>
          </thead>
          <tbody>
            {policies.map((policy) =>
              policy.buyers.map((buyer, index) => (
                <tr key={`${policy.policyId}-${buyer.userId}-${index}`}>
                  <td>{policy.policyId}</td>
                  <td>{policy.policyName}</td>
                  <td>{policy.policyType}</td>
                  <td>₹{policy.coverage.toLocaleString()}</td>
                  <td>₹{policy.premium.toLocaleString()}</td>
                  <td>{policy.durationInYears}</td>
                  <td>{buyer.name}</td>
                  <td>{buyer.email}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      )}
    </div>
  );
}
