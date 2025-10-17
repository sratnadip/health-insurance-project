import React, { useState } from "react";
import axios from "axios";
import "./ContactForm.css";
import { useNavigate } from "react-router-dom";

export default function ContactForm() {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    dob: "",
    mobileNumber: "",
    correspondenceAddress: "",
    permanentAddress: "",
    panNumber: "",
    role: "", 
  });

  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8089/api/contact-form/add",
        formData
      );
      setMessage(`Registration successful! ID: ${response.data.id}`);
      setFormData({
        name: "",
        email: "",
        dob: "",
        mobileNumber: "",
        correspondenceAddress: "",
        permanentAddress: "",
        panNumber: "",
        role: "",
      });
    } catch (error) {
      console.error("API Error:", error.response?.data || error.message);
      setMessage(
        error.response?.data?.message || "Something went wrong. Please try again."
      );
    }
  };

  return (
    <div className="contact-form-page">
      <div className="close-button" onClick={() => navigate("/support")}>
        &times;
      </div>

      <form className="contact-form" onSubmit={handleSubmit}>
        <h2>Register as Agent</h2>

        <input
          type="text"
          name="name"
          placeholder="Full Name"
          value={formData.name}
          onChange={handleChange}
          required
        />
        <input
          type="email"
          name="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
          required
        />
        <input
          type="date"
          name="dob"
          value={formData.dob}
          onChange={handleChange}
          required
        />
        <input
          type="text"
          name="mobileNumber"
          placeholder="Mobile Number"
          value={formData.mobileNumber}
          onChange={handleChange}
          required
        />
        <textarea
          name="correspondenceAddress"
          placeholder="Correspondence Address"
          value={formData.correspondenceAddress}
          onChange={handleChange}
          required
        />
        <textarea
          name="permanentAddress"
          placeholder="Permanent Address"
          value={formData.permanentAddress}
          onChange={handleChange}
          required
        />
        <input
          type="text"
          name="panNumber"
          placeholder="PAN Number"
          value={formData.panNumber}
          onChange={handleChange}
          required
        />

        
        <select
          name="role"
          value={formData.role}
          onChange={handleChange}
          required
        >
          <option value="">Select Role</option>
          <option value="USER">USER</option>
          <option value="ADMIN">ADMIN</option>
          
        </select>

        <button type="submit">Submit</button>

        {message && <p className="response-message">{message}</p>}
      </form>
    </div>
  );
}
