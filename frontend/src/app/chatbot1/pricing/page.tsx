"use client";

import { useState, useEffect, useRef } from "react";
import { FaCheckCircle, FaRocket, FaCrown, FaStar, FaTimesCircle, FaSpinner, FaHome, FaReceipt } from "react-icons/fa";

// Import your actual components
import Footer from "@/components/combination/Footer_C";
import Header_C from "@/components/combination/Header_C";

// Define TypeScript interfaces
interface Package {
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

interface VNPayParams {
  vnp_Amount: string;
  vnp_BankCode: string;
  vnp_BankTranNo: string;
  vnp_CardType: string;
  vnp_OrderInfo: string;
  vnp_PayDate: string;
  vnp_ResponseCode: string;
  vnp_TmnCode: string;
  vnp_TransactionNo: string;
  vnp_TransactionStatus: string;
  vnp_TxnRef: string;
  vnp_SecureHash: string;
}

interface SubscriptionData {
  userId: string;
  usagePackageId: string;
  transactionMethod: string;
}

// VNPay Return Component
function VNPayReturn({ paymentData }: { paymentData: VNPayParams }) {
  const [processing, setProcessing] = useState(false);
  const [subscriptionCreated, setSubscriptionCreated] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [countdown, setCountdown] = useState(10);
  const hasTriggeredRef = useRef(false); 

  const isSuccess =
    paymentData.vnp_ResponseCode === "00" &&
    paymentData.vnp_TransactionStatus === "00";

  useEffect(() => {
    if (isSuccess && !hasTriggeredRef.current) {
      hasTriggeredRef.current = true; 
      createSubscription();
    }
  }, [isSuccess, paymentData]);

  // Countdown for auto redirect
  useEffect(() => {
    if (!processing) {
      const timer = setInterval(() => {
        setCountdown((prev) => {
          if (prev <= 1) {
            clearInterval(timer);
            redirectToHome();
            return 0;
          }
          return prev - 1;
        });
      }, 1000);

      return () => clearInterval(timer);
    }
  }, [processing]);

  const createSubscription = async () => {
    setProcessing(true);
    try {
      // Get saved package ID from localStorage
      const savedPackageId = localStorage.getItem('selectedPackageId');
      const savedUserId = localStorage.getItem('userId') || "7403c3f0-b874-4cd7-b4d1-a5a2c41fb874";

      if (!savedPackageId) {
        throw new Error('Không tìm thấy thông tin gói dịch vụ');
      }

      const subscriptionData: SubscriptionData = {
        userId: savedUserId,
        usagePackageId: savedPackageId,
        transactionMethod: "VNPAY"
      };

      const response = await fetch('http://localhost:8080/api/subscriptions/purchase', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(subscriptionData)
      });

      if (!response.ok) {
        throw new Error('Failed to create subscription');
      }

      setSubscriptionCreated(true);
      
      // Clear localStorage after successful subscription
      localStorage.removeItem('selectedPackageId');
      
    } catch (err) {
      setError(err instanceof Error ? err.message : 'An error occurred');
    } finally {
      setProcessing(false);
    }
  };

  const formatAmount = (amount: string) => {
    const numAmount = parseInt(amount) / 100;
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND'
    }).format(numAmount);
  };

  const formatDate = (dateString: string) => {
    if (dateString.length === 14) {
      const year = dateString.substr(0, 4);
      const month = dateString.substr(4, 2);
      const day = dateString.substr(6, 2);
      const hour = dateString.substr(8, 2);
      const minute = dateString.substr(10, 2);
      const second = dateString.substr(12, 2);
      
      return `${day}/${month}/${year} ${hour}:${minute}:${second}`;
    }
    return dateString;
  };

  const redirectToHome = () => {
    window.location.href = '/';
  };

  const backToPricing = () => {
    // Clear URL parameters and reload page
    window.history.replaceState({}, document.title, window.location.pathname);
    window.location.reload();
  };

  const getResponseMessage = (code: string) => {
    const messages: { [key: string]: string } = {
      '00': 'Giao dịch thành công',
      '07': 'Trừ tiền thành công. Giao dịch bị nghi ngờ (liên quan tới lừa đảo, giao dịch bất thường).',
      '09': 'Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng chưa đăng ký dịch vụ InternetBanking tại ngân hàng.',
      '10': 'Giao dịch không thành công do: Khách hàng xác thực thông tin thẻ/tài khoản không đúng quá 3 lần',
      '11': 'Giao dịch không thành công do: Đã hết hạn chờ thanh toán. Xin quý khách vui lòng thực hiện lại giao dịch.',
      '12': 'Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng bị khóa.',
      '13': 'Giao dịch không thành công do Quý khách nhập sai mật khẩu xác thực giao dịch (OTP).',
      '24': 'Giao dịch không thành công do: Khách hàng hủy giao dịch',
      '51': 'Giao dịch không thành công do: Tài khoản của quý khách không đủ số dư để thực hiện giao dịch.',
      '65': 'Giao dịch không thành công do: Tài khoản của Quý khách đã vượt quá hạn mức giao dịch trong ngày.',
      '75': 'Ngân hàng thanh toán đang bảo trì.',
      '79': 'Giao dịch không thành công do: KH nhập sai mật khẩu thanh toán quá số lần quy định.',
      '99': 'Các lỗi khác (lỗi còn lại, không có trong danh sách mã lỗi đã liệt kê)'
    };
    return messages[code] || 'Lỗi không xác định';
  };

  

  return (
    <section className="min-h-screen bg-gradient-to-br from-gray-50 to-blue-50 py-12">
      <div className="max-w-4xl mx-auto px-6">
        <div className="bg-white rounded-2xl shadow-xl p-8">
          {/* Header */}
          <div className="text-center mb-8">
            {isSuccess ? (
              <div className="mb-6">
                <FaCheckCircle className="text-6xl text-green-500 mx-auto mb-4" />
                <h1 className="text-3xl font-bold text-green-600 mb-2">
                  Thanh toán thành công!
                </h1>
                <p className="text-gray-600">
                  Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi
                </p>
              </div>
            ) : (
              <div className="mb-6">
                <FaTimesCircle className="text-6xl text-red-500 mx-auto mb-4" />
                <h1 className="text-3xl font-bold text-red-600 mb-2">
                  Thanh toán thất bại!
                </h1>
                <p className="text-gray-600">
                  {getResponseMessage(paymentData.vnp_ResponseCode)}
                </p>
              </div>
            )}
          </div>

          {/* Transaction Details */}
          <div className="bg-gray-50 rounded-xl p-6 mb-8">
            <h2 className="text-xl font-semibold mb-4 flex items-center gap-2">
              <FaReceipt className="text-blue-500" />
              Thông tin giao dịch
            </h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <p className="text-sm text-gray-600">Mã giao dịch</p>
                <p className="font-semibold">{paymentData.vnp_TxnRef}</p>
              </div>
              <div>
                <p className="text-sm text-gray-600">Số tiền</p>
                <p className="font-semibold text-lg text-blue-600">
                  {formatAmount(paymentData.vnp_Amount)}
                </p>
              </div>
              <div>
                <p className="text-sm text-gray-600">Ngân hàng</p>
                <p className="font-semibold">{paymentData.vnp_BankCode}</p>
              </div>
              <div>
                <p className="text-sm text-gray-600">Loại thẻ</p>
                <p className="font-semibold">{paymentData.vnp_CardType}</p>
              </div>
              <div>
                <p className="text-sm text-gray-600">Thời gian</p>
                <p className="font-semibold">{formatDate(paymentData.vnp_PayDate)}</p>
              </div>
              <div>
                <p className="text-sm text-gray-600">Mã phản hồi</p>
                <p className="font-semibold">{paymentData.vnp_ResponseCode}</p>
              </div>
            </div>
            <div className="mt-4">
              <p className="text-sm text-gray-600">Thông tin đơn hàng</p>
              <p className="font-semibold">{decodeURIComponent(paymentData.vnp_OrderInfo)}</p>
            </div>
          </div>

          {/* Subscription Status */}
          {isSuccess && (
            <div className="bg-blue-50 rounded-xl p-6 mb-8">
              <h3 className="text-lg font-semibold mb-4 text-blue-800">
                Trạng thái kích hoạt gói dịch vụ
              </h3>
              {processing ? (
                <div className="flex items-center gap-3">
                  <FaSpinner className="animate-spin text-blue-500" />
                  <span className="text-blue-700">Đang kích hoạt gói dịch vụ...</span>
                </div>
              ) : subscriptionCreated ? (
                <div className="flex items-center gap-3">
                  <FaCheckCircle className="text-green-500" />
                  <span className="text-green-700">Gói dịch vụ đã được kích hoạt thành công!</span>
                </div>
              ) : error ? (
                <div className="flex items-center gap-3">
                  <FaTimesCircle className="text-red-500" />
                  <span className="text-red-700">Lỗi kích hoạt: {error}</span>
                </div>
              ) : null}
            </div>
          )}

          {/* Actions */}
          <div className="flex flex-col sm:flex-row gap-4 justify-center items-center">
            <button
              onClick={redirectToHome}
              className="flex items-center gap-2 bg-blue-500 hover:bg-blue-600 text-white font-semibold py-3 px-6 rounded-xl transition-colors"
            >
              <FaHome />
              Về trang chủ
            </button>
            
            <button
              onClick={backToPricing}
              className="flex items-center gap-2 bg-gray-500 hover:bg-gray-600 text-white font-semibold py-3 px-6 rounded-xl transition-colors"
            >
              Xem gói khác
            </button>

            {isSuccess && (
              <button
                onClick={() => window.location.href = '/profile'}
                className="flex items-center gap-2 bg-green-500 hover:bg-green-600 text-white font-semibold py-3 px-6 rounded-xl transition-colors"
              >
                Xem gói dịch vụ
              </button>
            )}
          </div>

          {/* Auto redirect countdown */}
          {!processing && (
            <div className="text-center mt-6 text-gray-600">
              <p>Tự động chuyển về trang chủ sau {countdown} giây...</p>
            </div>
          )}
        </div>
      </div>
    </section>
  );
}

// Main Pricing Component
function Pricing() {
  const [packages, setPackages] = useState<Package[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [vnpayParams, setVnpayParams] = useState<VNPayParams | null>(null);

  useEffect(() => {
    // Check if this is a VNPay return
    const urlParams = new URLSearchParams(window.location.search);
    
    if (urlParams.has('vnp_ResponseCode')) {
      // This is a VNPay return, parse the parameters
      const params: VNPayParams = {
        vnp_Amount: urlParams.get('vnp_Amount') || '',
        vnp_BankCode: urlParams.get('vnp_BankCode') || '',
        vnp_BankTranNo: urlParams.get('vnp_BankTranNo') || '',
        vnp_CardType: urlParams.get('vnp_CardType') || '',
        vnp_OrderInfo: urlParams.get('vnp_OrderInfo') || '',
        vnp_PayDate: urlParams.get('vnp_PayDate') || '',
        vnp_ResponseCode: urlParams.get('vnp_ResponseCode') || '',
        vnp_TmnCode: urlParams.get('vnp_TmnCode') || '',
        vnp_TransactionNo: urlParams.get('vnp_TransactionNo') || '',
        vnp_TransactionStatus: urlParams.get('vnp_TransactionStatus') || '',
        vnp_TxnRef: urlParams.get('vnp_TxnRef') || '',
        vnp_SecureHash: urlParams.get('vnp_SecureHash') || '',
      };
      setVnpayParams(params);
      setLoading(false);
      return;
    }

    // Normal pricing page, fetch packages
    fetchPackages();
  }, []);

  const fetchPackages = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/packages/active');
      if (!response.ok) throw new Error('Failed to fetch packages');
      const data = await response.json();
      setPackages(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'An error occurred');
    } finally {
      setLoading(false);
    }
  };

  const handlePayment = async (packageData: Package) => {
    try {
      // Save package ID to localStorage
      localStorage.setItem('selectedPackageId', packageData.id);
      
      const paymentData = {
        amount: packageData.price,
        orderInfo: `Payment for ${packageData.name} - ${packageData.description}`,
        transactionMethod: "VNPAY"
      };

      const response = await fetch('http://localhost:8080/api/vnpay/payment', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(paymentData)
      });

      if (!response.ok) throw new Error('Payment request failed');
      
      const result = await response.text();
      
      if (result.startsWith('redirect:')) {
        const paymentUrl = result.substring(9);
        window.location.href = paymentUrl;
      }
    } catch (err) {
      console.error('Payment error:', err);
      alert('Có lỗi xảy ra khi xử lý thanh toán. Vui lòng thử lại.');
    }
  };

  const getPackageIcon = (index: number) => {
    const icons = [FaRocket, FaStar, FaCrown];
    const IconComponent = icons[index % icons.length];
    return <IconComponent className="text-2xl mb-2" />;
  };

  const getPackageColors = (index: number) => {
    const colors = [
      { bg: 'from-blue-500 to-blue-600', text: 'text-blue-600', border: 'border-blue-200' },
      { bg: 'from-purple-500 to-purple-600', text: 'text-purple-600', border: 'border-purple-200' },
      { bg: 'from-green-500 to-green-600', text: 'text-green-600', border: 'border-green-200' },
    ];
    return colors[index % colors.length];
  };

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND'
    }).format(price);
  };

  // If this is a VNPay return, show the result page
  if (vnpayParams) {
    return <VNPayReturn paymentData={vnpayParams} />;
  }

  // Loading state
  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-gray-50 to-blue-50 flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-16 w-16 border-b-2 border-[#0069d1] mx-auto mb-4"></div>
          <p className="text-gray-600">Đang tải gói dịch vụ...</p>
        </div>
      </div>
    );
  }

  // Error state
  if (error) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-gray-50 to-blue-50 flex items-center justify-center">
        <div className="text-center">
          <div className="text-red-500 text-6xl mb-4">⚠️</div>
          <p className="text-red-600 text-lg">Lỗi: {error}</p>
        </div>
      </div>
    );
  }

  // Normal pricing page
  return (
    <section className="min-h-screen bg-gradient-to-br from-gray-50 via-blue-50 to-indigo-50 py-20">
      <div className="max-w-7xl mx-auto px-6">
        {/* Header */}
        <div className="text-center mb-16">
          <div className="inline-block p-3 bg-gradient-to-r from-blue-500 to-purple-600 rounded-full mb-6">
            <FaRocket className="text-white text-3xl" />
          </div>
          <h2 className="text-4xl md:text-5xl font-extrabold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent mb-4">
            Sử dụng không giới hạn AI chatbot
          </h2>
          <h3 className="text-2xl md:text-3xl font-bold text-gray-800 mb-4">
            Hỗ trợ tìm kiếm thông tin về luật giao thông
          </h3>
          <p className="text-lg text-gray-600 max-w-2xl mx-auto leading-relaxed">
            Bắt đầu ngay trong vài phút. Dùng thử miễn phí 7 ngày. Hủy bất cứ lúc nào.
          </p>
        </div>

        {/* Pricing Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 max-w-6xl mx-auto">
          {packages.map((pkg, index) => {
            const colors = getPackageColors(index);
            const isPopular = index === 1;
            
            return (
              <div
                key={pkg.id}
                className={`relative bg-white rounded-2xl shadow-xl hover:shadow-2xl transition-all duration-300 transform hover:-translate-y-2 ${
                  isPopular ? 'ring-4 ring-blue-500 ring-opacity-50 scale-105' : ''
                }`}
              >
                {isPopular && (
                  <div className="absolute -top-4 left-1/2 transform -translate-x-1/2">
                    <div className="bg-gradient-to-r from-blue-500 to-purple-600 text-white px-6 py-2 rounded-full text-sm font-bold shadow-lg">
                      🔥 PHỔ BIẾN NHẤT
                    </div>
                  </div>
                )}

                <div className="p-8">
                  <div className={`text-center mb-6 ${colors.text}`}>
                    {getPackageIcon(index)}
                  </div>

                  <h3 className="text-2xl font-bold text-center mb-2 text-gray-800">
                    {pkg.name}
                  </h3>

                  <div className="text-center mb-6">
                    <div className="text-4xl font-extrabold text-gray-900 mb-2">
                      {formatPrice(pkg.price)}
                    </div>
                    <div className="text-sm text-gray-500">
                      {pkg.daysLimit} ngày sử dụng
                    </div>
                  </div>

                  <p className="text-gray-600 text-center mb-6 leading-relaxed">
                    {pkg.description}
                  </p>

                  <div className={`border-t ${colors.border} pt-6 mb-8`}>
                    <h4 className="font-semibold mb-4 text-gray-900 flex items-center gap-2">
                      <FaCheckCircle className={colors.text} />
                      Tính năng bao gồm:
                    </h4>
                    <ul className="space-y-3">
                      <li className="flex items-start gap-3">
                        <FaCheckCircle className={`${colors.text} mt-1 flex-shrink-0`} />
                        <span className="text-gray-700">
                          {pkg.dailyLimit} lượt chat mỗi ngày
                        </span>
                      </li>
                      <li className="flex items-start gap-3">
                        <FaCheckCircle className={`${colors.text} mt-1 flex-shrink-0`} />
                        <span className="text-gray-700">
                          Sử dụng trong {pkg.daysLimit} ngày
                        </span>
                      </li>
                      <li className="flex items-start gap-3">
                        <FaCheckCircle className={`${colors.text} mt-1 flex-shrink-0`} />
                        <span className="text-gray-700">
                          Hỗ trợ 24/7
                        </span>
                      </li>
                      <li className="flex items-start gap-3">
                        <FaCheckCircle className={`${colors.text} mt-1 flex-shrink-0`} />
                        <span className="text-gray-700">
                          Cập nhật thông tin luật mới nhất
                        </span>
                      </li>
                    </ul>
                  </div>

                  <button
                    onClick={() => handlePayment(pkg)}
                    className={`w-full bg-gradient-to-r ${colors.bg} text-white font-bold py-4 px-6 rounded-xl hover:shadow-lg transition-all duration-300 transform hover:scale-105 focus:outline-none focus:ring-4 focus:ring-blue-300`}
                  >
                    Try Now
                  </button>

                  <div className="mt-4 text-center">
                    <p className="text-xs text-gray-500">
                      Thanh toán an toàn với VNPay
                    </p>
                  </div>
                </div>
              </div>
            );
          })}
        </div>

        {/* Bottom CTA */}
        <div className="text-center mt-16">
          <div className="bg-white rounded-2xl shadow-lg p-8 max-w-2xl mx-auto">
            <h3 className="text-2xl font-bold text-gray-800 mb-4">
              Cần hỗ trợ thêm?
            </h3>
            <p className="text-gray-600 mb-6">
              Liên hệ với đội ngũ hỗ trợ của chúng tôi để được tư vấn gói dịch vụ phù hợp nhất.
            </p>
            <button className="bg-gradient-to-r from-gray-600 to-gray-700 text-white font-bold py-3 px-8 rounded-xl hover:shadow-lg transition-all duration-300">
              Liên hệ hỗ trợ
            </button>
          </div>
        </div>
      </div>
    </section>
  );
}

export default function Page() {
  return (
    <>
      <Header_C />
      <Pricing />
      <Footer />
    </>
  );
}