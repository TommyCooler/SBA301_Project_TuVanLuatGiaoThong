"use client";

import React, { useEffect, useState } from "react";
import {
  Box,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Button,
  IconButton,
  TextField,
  Toolbar,
  Container,
  Switch,
  CircularProgress,
  Alert,
} from "@mui/material";
import LockIcon from "@mui/icons-material/Lock";
import LockOpenIcon from "@mui/icons-material/LockOpen";
import HeaderTop_C from "@/components/combination/HeaderTop_C";
import Footer_C from "@/components/combination/Footer_C";
import { sampleUser } from "@/data/sample";

interface UserPackage {
  userPackageId: number;
  userId: string;
  userName: string;
  usagePackageId: string;
  packageName: string;
  packagePrice: number;
  dailyLimit: number;
  daysLimit: number;
  transactionDate: string;
  transactionMethod: string;
  expirationDate: string;
  status: string;
  createdDate: string;
  updatedDate: string;
}

const AdminUserPackagePage: React.FC = () => {
  const [subscriptions, setSubscriptions] = useState<UserPackage[]>([]);
  const [search, setSearch] = useState<string>("");
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [apiError, setApiError] = useState<string | null>(null);

  // Fetch all subscriptions
  const fetchSubscriptions = async () => {
    setIsLoading(true);
    setApiError(null);
    try {
      const res = await fetch("http://localhost:8080/api/subscriptions", {
        headers: {
          "Content-Type": "application/json",
        },
      });
      if (!res.ok) throw new Error("Không thể tải danh sách gói đăng ký.");
      const data = await res.json();
      setSubscriptions(data);
    } catch (err) {
      setApiError(err.message || "Đã xảy ra lỗi khi tải dữ liệu.");
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchSubscriptions();
  }, []);

  // Toggle block/unblock status
  const toggleStatus = async (subscription: UserPackage) => {
    setApiError(null);
    try {
      const endpoint = subscription.status === "ACTIVE"
        ? `http://localhost:8080/api/subscriptions/${subscription.userPackageId}/block`
        : `http://localhost:8080/api/subscriptions/${subscription.userPackageId}/unblock`;
      const res = await fetch(endpoint, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
      });
      if (!res.ok) throw new Error("Không thể cập nhật trạng thái gói.");
      fetchSubscriptions();
    } catch (err) {
      setApiError(err.message || "Đã xảy ra lỗi khi cập nhật trạng thái.");
    }
  };

  // Filter subscriptions based on search
  const filtered = subscriptions.filter(
    (sub) =>
      sub.packageName.toLowerCase().includes(search.toLowerCase()) ||
      sub.userName.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <Box sx={{ display: "flex", flexDirection: "column", minHeight: "100vh" }}>
      <HeaderTop_C logedUser={sampleUser} />
      <Box component="main" sx={{ flexGrow: 1, p: { xs: 2, md: 4 } }}>
        <Container maxWidth="xl">
          <Typography variant="h4" gutterBottom fontWeight="bold">
            Quản lý Gói Đăng Ký Người Dùng
          </Typography>

          {apiError && (
            <Alert severity="error" sx={{ mb: 2 }}>
              {apiError}
            </Alert>
          )}

          <Toolbar sx={{ justifyContent: "space-between", px: 0, mb: 2 }}>
            <TextField
              label="Tìm kiếm theo tên gói hoặc người dùng"
              variant="outlined"
              size="small"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              sx={{ width: { xs: "100%", sm: 300 } }}
            />
          </Toolbar>

          {isLoading && !subscriptions.length ? (
            <Box sx={{ display: "flex", justifyContent: "center", my: 4 }}>
              <CircularProgress />
            </Box>
          ) : (
            <TableContainer component={Paper} sx={{ borderRadius: 2 }}>
              <Table>
                <TableHead sx={{ backgroundColor: "#f5f5f5" }}>
                  <TableRow>
                    <TableCell>Tên người dùng</TableCell>
                    <TableCell>Tên gói</TableCell>
                    <TableCell>Giá (VND)</TableCell>
                    <TableCell>Lượt chat/ngày</TableCell>
                    <TableCell>Số ngày</TableCell>
                    <TableCell>Ngày giao dịch</TableCell>
                    <TableCell>Ngày hết hạn</TableCell>
                    <TableCell>Phương thức</TableCell>
                    <TableCell>Trạng thái</TableCell>
                    <TableCell align="right">Hành động</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {filtered.map((sub) => (
                    <TableRow key={sub.userPackageId} hover>
                      <TableCell>{sub.userName}</TableCell>
                      <TableCell>{sub.packageName}</TableCell>
                      <TableCell>{sub.packagePrice.toLocaleString()}</TableCell>
                      <TableCell>{sub.dailyLimit}</TableCell>
                      <TableCell>{sub.daysLimit}</TableCell>
                      <TableCell>
                        {new Date(sub.transactionDate).toLocaleDateString("vi-VN")}
                      </TableCell>
                      <TableCell>
                        {new Date(sub.expirationDate).toLocaleDateString("vi-VN")}
                      </TableCell>
                      <TableCell>{sub.transactionMethod}</TableCell>
                      <TableCell>{sub.status === "ACTIVE" ? "Hoạt động" : "Đã khóa"}</TableCell>
                      <TableCell align="right">
                        <Switch
                          checked={sub.status === "ACTIVE"}
                          onChange={() => toggleStatus(sub)}
                          icon={<LockIcon />}
                          checkedIcon={<LockOpenIcon />}
                          aria-label={`Toggle trạng thái gói ${sub.packageName} của ${sub.userName}`}
                        />
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          )}
        </Container>
      </Box>
      <Footer_C />
    </Box>
  );
};

export default AdminUserPackagePage;