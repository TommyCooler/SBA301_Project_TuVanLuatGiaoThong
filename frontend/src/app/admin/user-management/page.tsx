import React from 'react';
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
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import HeaderTop_C from '@/components/combination/HeaderTop_C';
import Footer_C from '@/components/combination/Footer_C';
import { sampleUser } from '@/data/sample';

interface User {
  id: number;
  name: string;
  email: string;
  role: string;
}

const users: User[] = [
  { id: 1, name: 'Alice Johnson', email: 'alice@example.com', role: 'Admin' },
  { id: 2, name: 'Bob Smith', email: 'bob@example.com', role: 'User' },
  { id: 3, name: 'Charlie Lee', email: 'charlie@example.com', role: 'Moderator' },
];

const UserManagementPage: React.FC = () => {
  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <HeaderTop_C logedUser={sampleUser} />

      <Box sx={{ display: 'flex', flexGrow: 1 }}>
        <Box component="main" sx={{ flexGrow: 1, p: 4, width: '100%' }}>
          <Container maxWidth="xl">
            <Typography variant="h4" gutterBottom fontWeight={'bold'}>
              Quản lý Người Dùng
            </Typography>

            <Toolbar sx={{ justifyContent: 'space-between', paddingX: 0 }}>
              <TextField label="Tìm kiếm" variant="outlined" size="small" />
              <Button variant="contained" color="primary">
                Điều chỉnh
              </Button>
            </Toolbar>

            <TableContainer 
              component={Paper} 
              sx={{ marginTop: 2,
                border: '1px solid #e0e0e0',
                borderRadius: 2,
                boxShadow: 'none'
               }}>
              <Table>
                <TableHead sx={{ backgroundColor: '#f5f5f5' }}>
                  <TableRow>
                    <TableCell>ID</TableCell>
                    <TableCell>Tên người dùng</TableCell>
                    <TableCell>Email</TableCell>
                    <TableCell>Vai trò</TableCell>
                    <TableCell align="right">Hành động</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {users.map((user) => (
                    <TableRow key={user.id}>
                      <TableCell>{user.id}</TableCell>
                      <TableCell>{user.name}</TableCell>
                      <TableCell>{user.email}</TableCell>
                      <TableCell>{user.role}</TableCell>
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
          </Container>
        </Box>
      </Box>

      <Footer_C />
    </Box>
  );
};

export default UserManagementPage;
