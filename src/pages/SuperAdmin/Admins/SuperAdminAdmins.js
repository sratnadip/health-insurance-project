import React, { useState } from "react";
import { Tabs, Tab, Box, Typography } from "@mui/material";
import SuperAdminSidebar from "../SuperAdminSidebar";
import SuperAdminNavbar from "../SuperAdminNavbar";
import AdminList from "./AdminList";
import AdminApprovals from "./AdminApprovals";
import AdminRegistration from "./AdminRegistration"; // <-- New import

export default function SuperAdminAdmins() {
  const [activeTab, setActiveTab] = useState(0);

  return (
    <div style={{ display: "flex", minHeight: "100vh" }}>
      <SuperAdminSidebar />
      <div style={{ flex: 1, display: "flex", flexDirection: "column" }}>
        <SuperAdminNavbar />

        <div style={{ flex: 1, padding: "20px", background: "#f4f6f8" }}>
          <Typography variant="h5" gutterBottom>
            ⚙️ Manage Admins
          </Typography>

          <Tabs
            value={activeTab}
            onChange={(e, newValue) => setActiveTab(newValue)}
            textColor="primary"
            indicatorColor="primary"
            sx={{ mb: 3 }}
          >
            <Tab label="📋 Admin List" />
            <Tab label="✅ Admin Approvals" />
            <Tab label="📝 Admin Registration" /> {/* New tab */}
          </Tabs>

          <Box
            sx={{ background: "#fff", borderRadius: "8px", p: 3, minHeight: "400px" }}
          >
            {activeTab === 0 && <AdminList />}
            {activeTab === 1 && <AdminApprovals />}
            {activeTab === 2 && <AdminRegistration />} {/* New tab content */}
          </Box>
        </div>
      </div>
    </div>
  );
}
