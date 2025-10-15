import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { superAdminLogin } from "../../api/superAdminApi";

function SuperAdminLogin() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      const data = await superAdminLogin(email, password);
      if (data?.token) {
        localStorage.setItem("token", data.token);
        localStorage.setItem("role", "SUPER_ADMIN");
        localStorage.setItem("email", email);
        navigate("/superadmin/dashboard", { replace: true });
      } else {
        throw new Error("Login failed: No token received");
      }
    } catch (err) {
      console.error("Login error:", err);
      setError(err.message || "Login failed. Try again.");
    } finally {
      setLoading(false);
    }
  };

  // 👇 Navigate to Home Dashboard on cross click
  const handleClose = () => {
    navigate("/auth"); // Change "/home" to your home route
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        {/* Cross (×) Button */}
        <button onClick={handleClose} style={styles.closeButton}>
          &times;
        </button>

        <h2 style={styles.title}>Super Admin Login</h2>
        <p style={styles.subtitle}>Access the admin control panel</p>

        <form onSubmit={handleSubmit} style={styles.form}>
          {error && <p style={styles.error}>{error}</p>}

          <div style={styles.formGroup}>
            <label style={styles.label}>Email Address</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              placeholder="Enter your email"
              style={styles.input}
            />
          </div>

          <div style={styles.formGroup}>
            <label style={styles.label}>Password</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              placeholder="Enter your password"
              style={styles.input}
            />
          </div>

          <button type="submit" disabled={loading} style={styles.button}>
            {loading ? "Logging in..." : "Login"}
          </button>
        </form>
      </div>
    </div>
  );
}

const styles = {
  container: {
    minHeight: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    background: "#f5f6fa",
    fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
    padding: "10px",
  },
  card: {
    position: "relative", // 👈 important for cross positioning
    width: "100%",
    maxWidth: "400px",
    background: "#fff",
    borderRadius: "12px",
    boxShadow: "0 8px 20px rgba(0,0,0,0.1)",
    padding: "40px 30px",
  },
  closeButton: {
    position: "absolute",
    top: "10px",
    right: "15px",
    background: "transparent",
    border: "none",
    fontSize: "22px",
    fontWeight: "bold",
    color: "#999",
    cursor: "pointer",
    transition: "color 0.2s ease",
  },
  title: {
    fontSize: "26px",
    fontWeight: "700",
    marginBottom: "8px",
    color: "#007bff",
    textAlign: "center",
  },
  subtitle: {
    fontSize: "14px",
    color: "#666",
    marginBottom: "25px",
    textAlign: "center",
  },
  form: {
    marginTop: "10px",
  },
  formGroup: {
    marginBottom: "18px",
    textAlign: "left",
  },
  label: {
    display: "block",
    marginBottom: "6px",
    fontSize: "14px",
    fontWeight: "600",
    color: "#333",
  },
  input: {
    width: "100%",
    padding: "12px",
    border: "1px solid #ccc",
    borderRadius: "8px",
    outline: "none",
    fontSize: "14px",
    background: "#f9f9f9",
  },
  button: {
    width: "100%",
    padding: "14px",
    background: "#007bff",
    color: "#fff",
    border: "none",
    borderRadius: "8px",
    cursor: "pointer",
    fontSize: "16px",
    fontWeight: "600",
  },
  error: {
    color: "red",
    marginBottom: "15px",
    fontSize: "14px",
  },
};

export default SuperAdminLogin;
