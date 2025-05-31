export async function makePayment(amount: number) {
  // Giả lập API thanh toán
  return new Promise((resolve) => setTimeout(() => {
    console.log(`Thanh toán số tiền: ${amount}`);
    resolve(undefined);
  }, 2000));

  // Thực tế bạn có thể gọi đến Stripe, VNPay, v.v.
  // return await axios.post('/api/payment', { amount });
}
