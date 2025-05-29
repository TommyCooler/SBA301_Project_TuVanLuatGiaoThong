"use client";

import { useEffect, useState } from "react";
import {
  Box,
  TextField,
  Button,
  Avatar,
  IconButton,
  Paper,
  Stack,
  Typography,
  CircularProgress,
} from "@mui/material";
import PhotoCamera from "@mui/icons-material/PhotoCamera";

type UserInfo = {
  fullName: string;
  email: string;
  phone: string;
  address: string;
  avatar: string;
};

export default function PersonalInfoForm() {
  const [formData, setFormData] = useState<UserInfo>({
    fullName: "",
    email: "",
    phone: "",
    address: "",
    avatar: "",
  });

  const [loading, setLoading] = useState(true);

  // Giả lập gọi API lấy thông tin user
  useEffect(() => {
    const fetchUserInfo = async () => {
      // ⏳ Giả lập delay
      await new Promise(res => setTimeout(res, 1000));

      // ✅ Dữ liệu giả (thay bằng API thực nếu có)
      const userInfo: UserInfo = {
        fullName: "Nguyễn Văn A",
        email: "nguyenvana@example.com",
        phone: "0901234567",
        address: "123 Đường ABC, Quận 1, TP.HCM",
        avatar: "/avatar-sample.png",
      };

      setFormData(userInfo);
      setLoading(false);
    };

    fetchUserInfo();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      console.log("Updating user info:", formData);
    } catch (error) {
      console.error("Error updating profile:", error);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="60vh">
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      noValidate
      sx={{
        maxWidth: 500,
        mx: "auto",
        px: 3,
        py: 5,
      }}
    >
      <Paper elevation={6} sx={{ p: 4, borderRadius: 4 }}>
        <Stack spacing={3}>
          <Typography variant="h5" fontWeight="bold" textAlign="center" mb={1}>
            Thông tin cá nhân
          </Typography>

          <Box display="flex" justifyContent="center">
            <Box position="relative">
              <Avatar
                sx={{
                  width: 120,
                  height: 120,
                  border: "3px solid #ff9800",
                  boxShadow: 4,
                }}
                src={formData.avatar || "/default-avatar.png"}
              />
              <IconButton
                sx={{
                  position: "absolute",
                  bottom: 0,
                  right: 0,
                  backgroundColor: "#fff",
                  border: "1px solid #ccc",
                  boxShadow: 2,
                  "&:hover": {
                    backgroundColor: "#f5f5f5",
                  },
                }}
                component="label"
              >
                <input hidden accept="image/*" type="file" />
                <PhotoCamera />
              </IconButton>
            </Box>
          </Box>

          <TextField
            fullWidth
            name="email"
            label="Email"
            value={formData.email}
            disabled
            helperText="Email không thể thay đổi"
          />

          <TextField
            required
            fullWidth
            name="fullName"
            label="Họ và tên"
            value={formData.fullName}
            onChange={handleChange}
          />

          <TextField
            required
            fullWidth
            name="phone"
            label="Số điện thoại"
            value={formData.phone}
            onChange={handleChange}
          />

          <TextField
            fullWidth
            name="address"
            label="Địa chỉ"
            value={formData.address}
            onChange={handleChange}
            multiline
            rows={2}
          />

          <Button
            type="submit"
            variant="contained"
            size="large"
            fullWidth
            sx={{
              borderRadius: 3,
              py: 1.4,
              background: "linear-gradient(45deg, #1976d2, #42a5f5)",
              color: "#fff",
              fontWeight: "bold",
              textTransform: "none",
              "&:hover": {
                background: "linear-gradient(45deg, #1565c0, #2196f3)",
              },
            }}
          >
            Cập nhật thông tin
          </Button>
        </Stack>
      </Paper>
    </Box>
  );
}
