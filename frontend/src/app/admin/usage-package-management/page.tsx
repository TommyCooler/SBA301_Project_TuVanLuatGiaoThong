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
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import HeaderTop_C from "@/components/combination/HeaderTop_C";
import Footer_C from "@/components/combination/Footer_C";
import { sampleUser } from "@/data/sample";

interface UsagePackage {
  id: string;
  name: string;
  description: string;
  price: number;
  dailyLimit: number;
  daysLimit: number;
  isEnable: boolean;
  createdDate: string;
  updateDate: string;
}

const UsagePackageManagementPage: React.FC = () => {
  const [packages, setPackages] = useState<UsagePackage[]>([]);
  const [search, setSearch] = useState("");
  const [openDialog, setOpenDialog] = useState(false);
  const [form, setForm] = useState({
    name: "",
    description: "",
    price: 0,
    dailyLimit: 0,
    daysLimit: 0,
  });

  const fetchPackages = async () => {
    const res = await fetch("http://localhost:8080/api/packages");
    const data = await res.json();
    setPackages(data);
  };

  useEffect(() => {
    fetchPackages();
  }, []);

  const toggleEnable = async (pkg: UsagePackage) => {
    await fetch(
      `http://localhost:8080/api/packages/${pkg.id}/${pkg.isEnable ? "disable" : "enable"}`,
      { method: "PATCH" }
    );
    fetchPackages();
  };

  const handleCreate = async () => {
    await fetch("http://localhost:8080/api/packages", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form),
    });
    setOpenDialog(false);
    fetchPackages();
  };

  const filtered = packages.filter((p) =>
    p.name.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <Box sx={{ display: "flex", flexDirection: "column", minHeight: "100vh" }}>
      <HeaderTop_C logedUser={sampleUser} />
      <Box component="main" sx={{ flexGrow: 1, p: 4 }}>
        <Container maxWidth="xl">
          <Typography variant="h4" gutterBottom fontWeight="bold">
            Quản lý Gói Dịch Vụ
          </Typography>

          <Toolbar sx={{ justifyContent: "space-between", paddingX: 0, mb: 2 }}>
            <TextField
              label="Tìm kiếm"
              variant="outlined"
              size="small"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              sx={{ width: 300 }}
            />
            <Button variant="contained" color="primary" onClick={() => setOpenDialog(true)}>
              Thêm Gói
            </Button>
          </Toolbar>

          <TableContainer component={Paper} sx={{ borderRadius: 2 }}>
            <Table>
              <TableHead sx={{ backgroundColor: "#f5f5f5" }}>
                <TableRow>
                  <TableCell>Tên gói</TableCell>
                  <TableCell>Mô tả</TableCell>
                  <TableCell>Giá (VND)</TableCell>
                  <TableCell>Lượt chat/ngày</TableCell>
                  <TableCell>Số ngày</TableCell>
                  <TableCell>Kích hoạt</TableCell>
                  <TableCell align="right">Hành động</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {filtered.map((pkg) => (
                  <TableRow key={pkg.id} hover>
                    <TableCell>{pkg.name}</TableCell>
                    <TableCell>{pkg.description}</TableCell>
                    <TableCell>{pkg.price.toLocaleString()}</TableCell>
                    <TableCell>{pkg.dailyLimit}</TableCell>
                    <TableCell>{pkg.daysLimit}</TableCell>
                    <TableCell>
                      <Switch
                        checked={pkg.isEnable}
                        onChange={() => toggleEnable(pkg)}
                      />
                    </TableCell>
                    <TableCell align="right">
                      <IconButton color="primary">
                        <EditIcon />
                      </IconButton>
                      <IconButton color="error">
                        <DeleteIcon />
                      </IconButton>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>

          <Dialog open={openDialog} onClose={() => setOpenDialog(false)}>
            <DialogTitle>Thêm Gói Dịch Vụ</DialogTitle>
            <DialogContent sx={{ display: "flex", flexDirection: "column", gap: 2, mt: 1 }}>
              <TextField
                label="Tên gói"
                fullWidth
                value={form.name}
                onChange={(e) => setForm({ ...form, name: e.target.value })}
              />
              <TextField
                label="Mô tả"
                fullWidth
                value={form.description}
                onChange={(e) => setForm({ ...form, description: e.target.value })}
              />
              <TextField
                label="Giá"
                type="number"
                fullWidth
                value={form.price}
                onChange={(e) => setForm({ ...form, price: parseInt(e.target.value) })}
              />
              <TextField
                label="Lượt chat mỗi ngày"
                type="number"
                fullWidth
                value={form.dailyLimit}
                onChange={(e) => setForm({ ...form, dailyLimit: parseInt(e.target.value) })}
              />
              <TextField
                label="Số ngày"
                type="number"
                fullWidth
                value={form.daysLimit}
                onChange={(e) => setForm({ ...form, daysLimit: parseInt(e.target.value) })}
              />
            </DialogContent>
            <DialogActions>
              <Button onClick={() => setOpenDialog(false)}>Hủy</Button>
              <Button onClick={handleCreate} variant="contained">
                Tạo gói
              </Button>
            </DialogActions>
          </Dialog>
        </Container>
      </Box>
      <Footer_C />
    </Box>
  );
};

export default UsagePackageManagementPage;
