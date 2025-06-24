export async function makePayment(amount: number) {
  
  return new Promise((resolve) => setTimeout(() => {
    console.log(`Thanh toán số tiền: ${amount}`);
    resolve(undefined);
  }, 2000));


}
