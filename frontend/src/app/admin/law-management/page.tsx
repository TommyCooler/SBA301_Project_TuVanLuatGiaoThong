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

interface Law {
  id: number;
  part: string;
  law: string;
  content: string;
}

const laws: Law[] = [
  { id: 1, part: 'Chương I', law: 'Điều 1. Phạm vi điều chỉnh', content: 'Luật này quy định về quy tắc, phương tiện, người tham gia giao thông đường bộ, chỉ huy, điều khiển, tuần tra, kiểm soát, giải quyết tai nạn giao thông đường bộ, trách nhiệm quản lý nhà nước và trách nhiệm của cơ quan, tổ chức, cá nhân có liên quan đến trật tự, an toàn giao thông đường bộ.' },
  { id: 2, part: 'Chương I', law: 'Điều 2. Giải thích từ ngữ', content: '1. Trật tự, an toàn giao thông đường bộ là trạng thái giao thông trên đường bộ có trật tự, bảo đảm an toàn, thông suốt; được hình thành và điều chỉnh bởi các quy tắc, nguyên tắc, quy phạm pháp luật trong lĩnh vực giao thông đường bộ. 2. Phương tiện giao thông đường bộ là các loại xe, bao gồm: phương tiện giao thông cơ giới đường bộ (sau đây gọi là xe cơ giới), phương tiện giao thông thô sơ đường bộ (sau đây gọi là xe thô sơ), xe máy chuyên dùng và các loại xe tương tự. 3. Phương tiện tham gia giao thông đường bộ là phương tiện giao thông đường bộ tham gia giao thông trên đường bộ. 4. Đường ưu tiên là đường mà trên đó phương tiện tham gia giao thông đường bộ được các phương tiện tham gia giao thông đường bộ đến từ hướng khác nhường đường khi qua nơi đường giao nhau, được cắm biển báo hiệu đường ưu tiên. 5. Phần đường xe chạy là phần của đường bộ được sử dụng cho phương tiện giao thông đường bộ đi lại. 6. Làn đường là một phần của phần đường xe chạy được chia theo chiều dọc của đường, có đủ chiều rộng cho xe chạy an toàn. 7. Cải tạo xe (sau đây gọi là cải tạo) là việc thay đổi đặc điểm của xe đã được cấp đăng ký xe, biển số xe hoặc xe đã qua sử dụng được nhập khẩu dẫn đến thay đổi về kiểu loại xe theo quy định của cơ quan có thẩm quyền. 8. Người tham gia giao thông đường bộ bao gồm: người điều khiển, người được chở trên phương tiện tham gia giao thông đường bộ; người điều khiển, dẫn dắt vật nuôi trên đường bộ; người đi bộ trên đường bộ. 9. Người điều khiển phương tiện tham gia giao thông đường bộ bao gồm: người điều khiển xe cơ giới (sau đây gọi là người lái xe), người điều khiển xe thô sơ, người điều khiển xe máy chuyên dùng. 10. Người điều khiển giao thông đường bộ (sau đây gọi là người điều khiển giao thông) bao gồm: Cảnh sát giao thông và người được giao nhiệm vụ hướng dẫn giao thông trên đường bộ. 11. Ùn tắc giao thông đường bộ (sau đây gọi là ùn tắc giao thông) là tình trạng người, phương tiện tham gia giao thông đường bộ bị dồn ứ, di chuyển với tốc độ rất chậm hoặc không thể di chuyển được. 12. Tai nạn giao thông đường bộ là va chạm liên quan đến người, phương tiện khi tham gia giao thông đường bộ, xảy ra ngoài ý muốn của người tham gia giao thông đường bộ, gây thiệt hại đến tính mạng, sức khỏe, tài sản của cá nhân hoặc tài sản của cơ quan, tổ chức. 13. Thiết bị an toàn cho trẻ em là thiết bị có đủ khả năng bảo đảm an toàn cho trẻ em ở tư thế ngồi hoặc nằm trên xe ô tô, được thiết kế để giảm nguy cơ chấn thương cho người dùng trong trường hợp xảy ra va chạm hoặc xe ô tô giảm tốc độ đột ngột, bằng cách hạn chế sự di chuyển của cơ thể trẻ em. 14. Thiết bị thông minh hỗ trợ chỉ huy, điều khiển giao thông đường bộ là thiết bị kỹ thuật công nghệ có khả năng phát hiện, phân tích, đánh giá các tình huống giao thông đường bộ, vi phạm pháp luật trên đường bộ; do lực lượng chức năng sử dụng để hỗ trợ chỉ huy, điều khiển giao thông đường bộ, giải quyết tình huống và xử lý vi phạm pháp luật trên đường bộ.' },
  { id: 3, part: 'Chương II', law: 'Điều 10. Quy tắc chung', content: '1. Tuân thủ Hiến pháp, pháp luật Việt Nam và điều ước quốc tế mà nước Cộng hòa xã hội chủ nghĩa Việt Nam là thành viên. 2. Bảo đảm giao thông đường bộ được trật tự, an toàn, thông suốt, góp phần phát triển kinh tế - xã hội, bảo đảm quốc phòng, an ninh và bảo vệ môi trường; phòng ngừa vi phạm pháp luật về trật tự, an toàn giao thông đường bộ, tai nạn giao thông đường bộ và ùn tắc giao thông; bảo vệ tính mạng, sức khỏe, tài sản của cá nhân và tài sản của cơ quan, tổ chức. 3. Bảo đảm trật tự, an toàn giao thông đường bộ là trách nhiệm của cơ quan, tổ chức, cá nhân. 4. Người tham gia giao thông đường bộ phải chấp hành các quy định của pháp luật về trật tự, an toàn giao thông đường bộ và quy định khác của pháp luật có liên quan, có trách nhiệm giữ an toàn cho mình và cho người khác. 5. Mọi hành vi vi phạm pháp luật về trật tự, an toàn giao thông đường bộ phải được phát hiện, ngăn chặn kịp thời và phải bị xử lý nghiêm minh theo quy định của pháp luật. 6. Hoạt động bảo đảm trật tự, an toàn giao thông đường bộ phải công khai, minh bạch và thuận lợi cho người dân. 7. Công tác bảo đảm trật tự, an toàn giao thông đường bộ được thực hiện thống nhất trên cơ sở phân công, phân cấp, phù hợp với chức năng, nhiệm vụ và sự phối hợp chặt chẽ giữa các cơ quan, tổ chức, cá nhân có liên quan.' },
];

const LawManagementPage: React.FC = () => {
  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <HeaderTop_C logedUser={sampleUser} />
      
      <Box sx={{ display: 'flex', flexGrow: 1 }}>
        <Box component="main" sx={{ flexGrow: 1, p: 4, width: '100%' }}>
          <Container maxWidth="xl">
            <Typography variant="h4" gutterBottom fontWeight="bold">
              Luật Hiện Hành
            </Typography>
            
            <Toolbar sx={{ justifyContent: 'space-between', paddingX: 0, mb: 2 }}>
              <TextField 
                label="Tìm kiếm" 
                variant="outlined" 
                size="small"
                sx={{ width: 300 }}
              />
              <Button 
                variant="contained" 
                color="primary" 
                sx={{ 
                  bgcolor: '#2a82d9', 
                  color: '#ffffff',
                  '&:hover': {
                    bgcolor: '#2a82d9',
                  }
                }}
              >
                Điều chỉnh Luật
              </Button>
            </Toolbar>

            <TableContainer 
              component={Paper} 
              sx={{ 
                marginTop: 2,
                border: '1px solid #e0e0e0',
                borderRadius: 2,
                boxShadow: 'none'
              }}
            >
              <Table>
                <TableHead sx={{ backgroundColor: '#f5f5f5' }}>
                  <TableRow>
                    <TableCell hidden>ID</TableCell>
                    <TableCell>Chương</TableCell>
                    <TableCell>Điều/Luật</TableCell>
                    <TableCell>Nội dung</TableCell>
                    <TableCell align="right">Hành động</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {laws.map((law) => (
                    <TableRow key={law.id} hover>
                      <TableCell hidden>{law.id}</TableCell>
                      <TableCell>{law.part}</TableCell>
                      <TableCell>{law.law}</TableCell>
                      <TableCell sx={{ maxWidth: 400, overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                        {law.content}
                      </TableCell>
                      <TableCell align="right">
                        <IconButton color="primary" sx={{ color: '#FFD700' }}>
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

export default LawManagementPage;
