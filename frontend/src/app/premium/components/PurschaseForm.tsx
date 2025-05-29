"use client";

import { useState } from "react";
import {
  Box,
  Button,
  Typography,
  CircularProgress,
  Card,
  CardContent,
  Stack,
} from "@mui/material";
import StarIcon from "@mui/icons-material/Star";
import { makePayment } from "@/lib/utils/payment";

export default function PurchaseForm() {
  const [loading, setLoading] = useState(false);
  const [paid, setPaid] = useState(false);

  const handlePurchase = async () => {
    setLoading(true);
    try {
      await makePayment(5);
      setPaid(true);
    } catch (error) {
      console.error(error);
      alert("Thanh toán thất bại. Vui lòng thử lại.");
    } finally {
      setLoading(false);
    }
  };

  if (paid) {
    return (
      <Box textAlign="center" mt={6}>
        <Typography color="success.main" variant="h5" fontWeight="bold">
          🎉 Bạn đã nâng cấp thành công lên Premium!
        </Typography>
      </Box>
    );
  }

  return (
    <Box display="flex" justifyContent="center" alignItems="center" minHeight="70vh">
      <Card sx={{ maxWidth: 400, width: "100%", p: 3, borderRadius: 4, boxShadow: 6 }}>
        <CardContent>
          <Stack spacing={2} alignItems="center">
            <StarIcon color="warning" sx={{ fontSize: 48 }} />
            <Typography variant="h5" fontWeight="bold">
              Nâng cấp lên Premium
            </Typography>
            <Typography variant="body1" textAlign="center" color="text.secondary">
              Mở khóa toàn bộ tính năng cao cấp với chỉ 5 USD!
            </Typography>
            <Button
              variant="contained"
              size="large"
              onClick={handlePurchase}
              disabled={loading}
              sx={{
                borderRadius: 3,
                px: 4,
                py: 1.5,
                background: "linear-gradient(45deg, #ff9800, #f44336)",
                color: "#fff",
                fontWeight: "bold",
                textTransform: "none",
                "&:hover": {
                  background: "linear-gradient(45deg, #f57c00, #e53935)",
                },
              }}
            >
              {loading ? <CircularProgress size={24} sx={{ color: "#fff" }} /> : "Thanh toán 5 USD"}
            </Button>
          </Stack>
        </CardContent>
      </Card>
    </Box>
  );
}
