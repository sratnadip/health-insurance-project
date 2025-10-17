import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import "./AvailablePolicies.css";

export default function AvailablePolicies() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [policy, setPolicy] = useState(null);
  const [userId, setUserId] = useState(null);
  const [showNomineeForm, setShowNomineeForm] = useState(false);
  const [nominee, setNominee] = useState("");
  const [relation, setRelation] = useState("");

  useEffect(() => {
    const authData = JSON.parse(localStorage.getItem("authData"));
    if (!authData || !authData.token) {
      alert("Please login to view policy details.");
      navigate("/auth", {
        state: { redirectAfterLogin: `/available-policies/${id}` },
      });
      return;
    }

    setUserId(authData.userId);

    axios
      .get(`http://localhost:8089/admin/policy-plans/${id}`)
      .then((res) => setPolicy(res.data))
      .catch((err) => {
        console.error("Error fetching policy:", err);
        alert("Unable to load policy details");
        navigate("/health-plans");
      });
  }, [id, navigate]);

  const handleBuyPolicy = async () => {
    if (!nominee || !relation) {
      alert("Please fill nominee details");
      return;
    }

    const payload = {
      userId: Number(userId),
      policyId: policy.id,
      nominee,
      nomineeRelation: relation,
    };

    try {
      await axios.post("http://localhost:8089/user-policy/purchase", payload);
      alert("✅ Policy purchased successfully!");
      navigate("/dashboard/policies");
    } catch (err) {
      console.error("Error buying policy:", err);
      alert("❌ Failed to buy policy");
    }
  };

  if (!policy) return <p className="loading-text">Loading policy details...</p>;

  return (
    <div className="policy-details-container">
      <h2 className="policy-name">{policy.policyName}</h2>

      <div className="policy-content">
        <div className="image-container">
          <img
            src={`http://localhost:8089/admin/policy-plans/view-image/${policy.id}`}
            alt={policy.policyName}
            className="policy-img"
          />
        </div>

        <div className="policy-info">
          <p><strong>Type:</strong> {policy.policyType}</p>
          <p><strong>Coverage:</strong> ₹{policy.coverage}</p>
          <p><strong>Premium:</strong> ₹{policy.premium}</p>
          <p><strong>Duration:</strong> {policy.durationInYears} years</p>
        </div>
      </div>

      {!showNomineeForm ? (
        <button className="buy-btn" onClick={() => setShowNomineeForm(true)}>
          Buy Policy
        </button>
      ) : (
        <div className="nominee-form">
          <h3>Nominee Details</h3>
          <input
            type="text"
            placeholder="Nominee Name"
            value={nominee}
            onChange={(e) => setNominee(e.target.value)}
          />
          <input
            type="text"
            placeholder="Nominee Relation"
            value={relation}
            onChange={(e) => setRelation(e.target.value)}
          />
          <button className="submit-btn" onClick={handleBuyPolicy}>
            Confirm Purchase
          </button>
        </div>
      )}
    </div>
  );
}
