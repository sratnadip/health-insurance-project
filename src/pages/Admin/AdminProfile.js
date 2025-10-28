import React, { useEffect, useState } from "react";
import { useFormik } from "formik";
import * as Yup from "yup";
import { useNavigate } from "react-router-dom";
import "../../pages/Admin/AdminProfile.css";
import {
  saveAdminProfile,
  getAdminProfile,
  updateAdminProfile,
} from "../AdminAPI/AdminProfileAPI";

export default function AdminProfileForm() {
  const navigate = useNavigate(); 
  const [mode, setMode] = useState("loading"); // "loading", "create", "view", "edit"
  const [profileId, setProfileId] = useState(null);
  const adminId = sessionStorage.getItem("adminId");
  const profileKey = `profileId_${adminId}`;

  // ✅ Field icons
  const fieldIcons = {
    name: "👤",
    email: "📧",
    password: "🔒",
    phoneNumber: "📱",
    dateOfBirth: "🎂",
    companyName: "🏢",
    companyType: "🏭",
    panNumber: "🧾",
    gstNumber: "💰",
    headOfficeAddress: "🏠",
    correspondenceAddress: "📮",
    permanentAddress: "📍",
    city: "🌆",
    state: "🗺️",
    country: "🌏",
    pinCode: "🔢",
  };

  // ✅ Validation schema
  const validationSchema = Yup.object({
    name: Yup.string()
      .matches(/^[A-Za-z\s]+$/, "Name must contain only letters")
      .required("Name is required"),
    email: Yup.string().email("Invalid email format").required("Email is required"),
    password: Yup.string()
      .min(8, "Password must be at least 8 characters long")
      .matches(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/,
        "Password must contain uppercase, lowercase, number, and special character"
      )
      .required("Password is required"),
    phoneNumber: Yup.string()
      .matches(/^[6-9]\d{9}$/, "Enter a valid 10-digit mobile number")
      .required("Phone number is required"),
    dateOfBirth: Yup.date()
      .max(new Date(), "Date of Birth cannot be in the future")
      .required("Date of Birth is required"),
    companyName: Yup.string().required("Company name is required"),
    companyType: Yup.string().required("Company type is required"),
    panNumber: Yup.string()
      .matches(/^[A-Z]{5}[0-9]{4}[A-Z]{1}$/, "Invalid PAN format (e.g., ABCDE1234F)")
      .required("PAN number is required"),
    gstNumber: Yup.string()
      .matches(
        /^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$/,
        "Invalid GST format (e.g., 27ABCDE1234F1Z5)"
      )
      .required("GST number is required"),
    headOfficeAddress: Yup.string().required("Head Office Address is required"),
    correspondenceAddress: Yup.string().required("Correspondence Address is required"),
    permanentAddress: Yup.string().required("Permanent Address is required"),
    city: Yup.string()
      .matches(/^[A-Za-z\s]+$/, "City must contain only letters")
      .required("City is required"),
    state: Yup.string()
      .matches(/^[A-Za-z\s]+$/, "State must contain only letters")
      .required("State is required"),
    country: Yup.string()
      .matches(/^[A-Za-z\s]+$/, "Country must contain only letters")
      .required("Country is required"),
    pinCode: Yup.string()
      .matches(/^[1-9][0-9]{5}$/, "Enter a valid 6-digit PIN code")
      .required("Pincode is required"),
  });

  // ✅ Formik setup
  const formik = useFormik({
    initialValues: {
      name: "",
      email: "",
      password: "",
      phoneNumber: "",
      dateOfBirth: "",
      companyName: "",
      companyType: "",
      panNumber: "",
      gstNumber: "",
      headOfficeAddress: "",
      correspondenceAddress: "",
      permanentAddress: "",
      city: "",
      state: "",
      country: "",
      pinCode: "",
    },
    validationSchema,
    onSubmit: async (values) => {
      if (!adminId) {
        alert("Admin not logged in!");
        return;
      }

      const payload = { ...values, admin: { id: parseInt(adminId, 10) } };

      try {
        if (mode === "edit" && profileId) {
          await updateAdminProfile(profileId, payload);
          alert("Profile Updated Successfully!");
        } else {
          const res = await saveAdminProfile(payload);
          if (res.id) {
            sessionStorage.setItem(profileKey, res.id);
            setProfileId(res.id);
          }
          alert("Profile Created Successfully!");
        }
        fetchProfile();
      } catch (err) {
        console.error("Error saving profile:", err);
        alert("Failed to save profile.");
      }
    },
  });

  // ✅ Fetch profile data
  const fetchProfile = async () => {
    if (!adminId) {
      setMode("create");
      return;
    }

    let storedProfileId = sessionStorage.getItem(profileKey);
    if (!storedProfileId) {
      setMode("create");
      return;
    }

    try {
      const data = await getAdminProfile(storedProfileId);
      if (data) {
        setProfileId(data.id);
        formik.setValues({
          name: data.name || "",
          email: data.email || "",
          password: data.password || "",
          phoneNumber: data.phoneNumber || "",
          dateOfBirth: data.dateOfBirth || "",
          companyName: data.companyName || "",
          companyType: data.companyType || "",
          panNumber: data.panNumber || "",
          gstNumber: data.gstNumber || "",
          headOfficeAddress: data.headOfficeAddress || "",
          correspondenceAddress: data.correspondenceAddress || "",
          permanentAddress: data.permanentAddress || "",
          city: data.city || "",
          state: data.state || "",
          country: data.country || "",
          pinCode: data.pinCode || "",
        });
        setMode("view");
      } else {
        setMode("create");
      }
    } catch (err) {
      console.error("Error fetching profile:", err);
      setMode("create");
    }
  };

  useEffect(() => {
    fetchProfile();
  }, [adminId]); // eslint-disable-line

  // ✅ Loading State
  if (mode === "loading") return <p>Loading...</p>;

  // ✅ View Mode
  if (mode === "view") {
    const values = formik.values;
    return (
      <div className="profile-overlay">
        <div className="form-container">
          {/* ✖ Close Button */}
          <button
            className="close-btn"
            onClick={() => navigate("/admin/dashboard")}
            title="Go to Dashboard"
          >
            ✖
          </button>

          <h2>Admin Profile</h2>
          <table className="profile-table">
            <tbody>
              {Object.keys(formik.initialValues).map((field) => (
                <tr key={field}>
                  <td className="field-name">
                    {fieldIcons[field]}{" "}
                    {field.charAt(0).toUpperCase() +
                      field.slice(1).replace(/([A-Z])/g, " $1")}
                  </td>
                  <td className="field-value">{values[field]}</td>
                </tr>
              ))}
            </tbody>
          </table>
          <div className="profile-actions">
            <button onClick={() => setMode("edit")} className="edit-btn blue-btn">
              Edit Profile
            </button>
          </div>
        </div>
      </div>
    );
  }

  // ✅ Create/Edit Form
  return (
    <div className="profile-overlay">
      <div className="form-container">
        <button
          className="close-btn"
          onClick={() => navigate("/admin/dashboard")}
          title="Go to Dashboard"
        >
          ✖
        </button>

        <h2>{mode === "edit" ? "Edit Admin Profile" : "Create Admin Profile"}</h2>
        <form onSubmit={formik.handleSubmit} className="form-grid">
          {Object.keys(formik.initialValues).map((field) => (
            <div key={field} className="form-group">
              <label>
                {fieldIcons[field]}{" "}
                {field.charAt(0).toUpperCase() + field.slice(1).replace(/([A-Z])/g, " $1")}
              </label>
              <input
                type={
                  field === "password"
                    ? "password"
                    : field === "dateOfBirth"
                    ? "date"
                    : "text"
                }
                {...formik.getFieldProps(field)}
              />
              {formik.touched[field] && formik.errors[field] && (
                <p className="error">{formik.errors[field]}</p>
              )}
            </div>
          ))}
          <div className="form-group full-width">
            <button type="submit" className="submit-btn">
              {mode === "edit" ? "Update Profile" : "Save Profile"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
