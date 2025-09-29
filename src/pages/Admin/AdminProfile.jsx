import React from "react";
import { useFormik } from "formik";
import * as Yup from "yup";
import "../../pages/Admin/AdminProfile.css";
import { saveAdminProfile } from "../AdminAPI/AdminProfileAPI";

export default function AdminProfileForm() {
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
      city: "",
      state: "",
      country: "",
      pinCode: "",
    },
    validationSchema: Yup.object({
      name: Yup.string().required("Name is required"),
      email: Yup.string().email("Invalid email").required("Email is required"),
      password: Yup.string().min(8).required("Password is required"),
      phoneNumber: Yup.string().required("Phone number is required"),
      dateOfBirth: Yup.string().required("Date of Birth is required"),
      companyName: Yup.string().required("Company name is required"),
      companyType: Yup.string().required("Company type is required"),
      panNumber: Yup.string().required("PAN number is required"),
      gstNumber: Yup.string().required("GST number is required"),
      headOfficeAddress: Yup.string().required("Head Office Address is required"),
      city: Yup.string().required("City is required"),
      state: Yup.string().required("State is required"),
      country: Yup.string().required("Country is required"),
      pinCode: Yup.string().required("Pincode is required"),
    }),
    onSubmit: async (values, { resetForm }) => {
      try {
        const adminId = sessionStorage.getItem("adminId");
        if (!adminId) {
          alert("Admin ID not found. Please log in again.");
          return;
        }

        // ✅ Ensure correct format for LocalDate
        const payload = {
          ...values,
          dateOfBirth: values.dateOfBirth, // yyyy-MM-dd
          admin: { id: parseInt(adminId, 10) }, // attach admin object
        };

        const res = await saveAdminProfile(payload);
        alert("Admin Profile Created Successfully!");
        console.log("✅ Saved Data:", res);
        resetForm();
      } catch (error) {
        console.error("❌ Error saving profile:", error);
        alert("Failed to create Admin Profile.");
      }
    },
  });

  return (
    <div className="form-container">
      <h2>Admin Profile Form</h2>
      <form onSubmit={formik.handleSubmit} className="form-grid">
        {Object.keys(formik.initialValues).map((field) => (
          <div key={field} className="form-group">
            <label>{field.replace(/([A-Z])/g, " $1")}</label>
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
          <button type="submit" className="submit-btn">Save Profile</button>
        </div>
      </form>
    </div>
  );
}
