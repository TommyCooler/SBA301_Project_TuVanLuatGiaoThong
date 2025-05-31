import { Box, Container } from "@mui/material";
import SectionTitle from "./components/SectionTitle";
import PurchaseForm from "./components/PurschaseForm";

export default function PremiumPage() {
  return (
    <Container maxWidth="sm" sx={{ py: 6 }}>
      <SectionTitle title="Nâng cấp tài khoản Premium" subtitle="Chỉ 5 USD cho quyền truy cập không giới hạn AI ChatBox" />
      <Box mt={4}>
        <PurchaseForm />
      </Box>
    </Container>
  );
}
