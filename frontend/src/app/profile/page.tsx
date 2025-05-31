"use client";

import { Container, Tab, Tabs, Box } from "@mui/material";
import { useState } from "react";
import PersonalInfoForm from "./components/PersonalInfoForm";
import ChangePasswordForm from "./components/ChangePasswordForm";

export default function ProfilePage() {
  const [activeTab, setActiveTab] = useState(0);

  const handleTabChange = (_: React.SyntheticEvent, newValue: number) => {
    setActiveTab(newValue);
  };

  return (
    <Container maxWidth="md" sx={{ py: 4 }}>
      <Box sx={{ width: '100%', bgcolor: 'background.paper' }}>
        <Tabs value={activeTab} onChange={handleTabChange} centered>
          <Tab label="Thông tin cá nhân" />
          <Tab label="Đổi mật khẩu" />
        </Tabs>

        <Box sx={{ p: 3 }}>
          {activeTab === 0 && <PersonalInfoForm />}
          {activeTab === 1 && <ChangePasswordForm />}
        </Box>
      </Box>
    </Container>
  );
}