// "use client";

// import React, { useState } from "react";
// import { FcGoogle } from "react-icons/fc";
// import { AiFillHome } from "react-icons/ai";
// import { useRouter } from "next/navigation";
// import { toast } from "sonner";
// import axios from "axios";


// export default function RegisterPage() {
//   const [email, setEmail] = useState("");
//   const [password, setPassword] = useState("");
//   const [fullname, setFullname] = useState("");
//   const [confirmPassword, setConfirmPassword] = useState("");
//   const router = useRouter();

//   const handleRegister = async (e: React.FormEvent) => {
//     e.preventDefault();
//   if (password !== confirmPassword) {
//     toast.error("Mật khẩu xác nhận không khớp!");
//     return;
//   }
//   try {
//     const response = await axios.post("http://localhost:8080/register", {
//       fullName: fullname, // chú ý chữ N hoa
//       email,
//       password,
//       confirmPassword,
//     });
//     if (response.status === 200 && response.data.status === "Success") {
//       toast.success("Đăng ký thành công! Vui lòng đăng nhập.");
//       setTimeout(() => {
//         router.push("/login");
//       }, 1500);
//     } else {
//       toast.error(response.data.message || "Đăng ký thất bại!");
//     }
//   } catch (error: any) {
//     if (axios.isAxiosError(error) && error.response) {
//       toast.error(error.response.data.message || "Đăng ký thất bại!");
//     } else {
//       toast.error("Đăng ký thất bại!");
//     }
//   }
//   };

//   return (
//     <div className="min-h-screen bg-gradient-to-br from-[#e3f0ff] to-[#f9f9f9] flex items-center justify-center font-sans">
//       <div className="flex flex-row bg-white rounded-[18px] shadow-[0_8px_32px_rgba(0,0,0,0.12)] overflow-hidden max-w-[850px] w-full">
//         {/* Bên trái */}
//         <div className="bg-[#f5faff] w-[340px] flex flex-col items-center justify-center p-8 min-h-[540px]">
//           <img
//             src="/AILogin.gif"
//             alt="Car animation"
//             className="w-[120px] mb-4"
//           />
//           <h1 className="text-[#0069d1] text-xl font-bold mb-2 text-center">
//             Chào mừng bạn!
//           </h1>
//           <p className="text-gray-800 text-center text-base leading-relaxed">
//             Đăng ký để sử dụng hệ thống tư vấn luật giao thông.
//             <br />
//             Chúng tôi luôn sẵn sàng hỗ trợ bạn!
//           </p>
//         </div>

//         {/* Bên phải - Form */}
//         <div className="flex-1 p-10 flex flex-col justify-center">
//           <h2 className="text-[#0069d1] text-2xl font-bold mb-6 text-center">
//             Đăng ký
//           </h2>
//           <form onSubmit={handleRegister} className="flex flex-col gap-4">
//             <input
//               type="text"
//               placeholder="Họ và tên"
//               value={fullname}
//               onChange={(e) => setFullname(e.target.value)}
//               required
//               className="px-4 py-3 border border-[#d1e3f8] rounded-md text-base focus:outline-none focus:border-[#0069d1] transition"
//             />
//             <input
//               type="email"
//               placeholder="Email"
//               value={email}
//               onChange={(e) => setEmail(e.target.value)}
//               required
//               className="px-4 py-3 border border-[#d1e3f8] rounded-md text-base focus:outline-none focus:border-[#0069d1] transition"
//             />
//             <input
//               type="password"
//               placeholder="Mật khẩu"
//               value={password}
//               onChange={(e) => setPassword(e.target.value)}
//               required
//               className="px-4 py-3 border border-[#d1e3f8] rounded-md text-base focus:outline-none focus:border-[#0069d1] transition"
//             />
//             <input
//               type="password"
//               placeholder="Xác nhận mật khẩu"
//               value={confirmPassword}
//               onChange={(e) => setConfirmPassword(e.target.value)}
//               required
//               className="px-4 py-3 border border-[#d1e3f8] rounded-md text-base focus:outline-none focus:border-[#0069d1] transition"
//             />

//             <button
//               type="submit"
//               className="bg-[#0069d1] text-white rounded-md py-3 font-semibold text-lg hover:bg-[#0051a8] transition shadow-md"
//             >
//               Đăng ký
//             </button>

//             <div className="relative my-2 text-center text-gray-400 text-sm">
//               <span className="bg-white px-3 relative z-10">Hoặc</span>
//               <div className="absolute top-1/2 left-0 w-full h-px bg-gray-200 -z-10"></div>
//             </div>

//             <button
//               type="button"
//               className="bg-gray-100 text-black rounded-md py-3 font-medium flex items-center justify-center gap-2 hover:bg-gray-200 transition"
//             >
//               <FcGoogle size={22} />
//               Đăng nhập với Google
//             </button>

//             <div className="text-center text-sm mt-2">
//               <span>Bạn đã có tài khoản? </span>
//               <a href="/login" className="text-[#0069d1] hover:underline">
//                 Đăng nhập
//               </a>
//             </div>

//             <div className="flex justify-center mt-3">
//               <a href="/" className="text-[#0069d1] hover:text-[#003d73]">
//                 <AiFillHome size={24} />
//               </a>
//             </div>
//           </form>
//         </div>
//       </div>
//     </div>
//   );
// }


"use client";

import {
  Form,
  FormField,
  FormItem,
  FormLabel,
  FormControl,
  FormMessage,
} from "@/components/modern-ui/form";
import { Input } from "@/components/modern-ui/input";
import { Button } from "@/components/modern-ui/button";
import { useForm } from "react-hook-form";
import * as z from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useState } from "react";
import { toast } from "sonner";
import { Color } from "@/configs/CssConstant";
import { FaArrowLeft } from "react-icons/fa";
import useAxios from "@/hooks/useAxios";
import { Api } from "@/configs/Api";
import HttpStatus from "@/configs/HttpStatus";
import { useRouter } from "next/navigation";
import axios from "axios";

const formSchema = z.object({
  // username: z.string().min(3, {
  //   message: "Tên người dùng phải có ít nhất 3 ký tự.",
  // }),
  email: z.string().email({
    message: "Email không hợp lệ.",
  }),
  password: z.string().min(5, {
    message: "Mật khẩu phải có ít nhất 5 ký tự.",
  }),
  confirmPassword: z.string().min(5, {
    message: "Vui lòng nhập lại mật khẩu.",
  }),
  fullName: z.string().min(1, {
    message: "Họ tên không được để trống.",
  }),
});

function RegisterForm() {

  const router = useRouter();

  const form = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      // username: "",
      password: "",
      email: "",
      confirmPassword: "",
      fullName: "",
    },
  });

  const [isLoading, setIsLoading] = useState(false);

  // Use form.handleSubmit for proper validation and submission
  const handleRegister = form.handleSubmit(async (data) => {
    // if (!data.username) {
    //   toast.error("Tên đăng nhập không được để trống!");
    //   return;
    // }
    if (!data.password) {
      toast.error("Mật khẩu không được để trống");
      return;
    }

    try {
      setIsLoading(true);
      const response = await axios.post(Api.BASE_API + Api.Authenticaion.REGISTER, {
        // username: data.username,
        email: data.email,
        password: data.password,
        fullName: data.fullName,
        confirmPassword: data.confirmPassword,
      })
      if (response.status == HttpStatus.OK && response.data.status == 'success') {
        toast.success("Mã OTP đã được gửi đến email của bạn, vui lòng kiểm tra và xác thực email");
        router.push(`/register/verify-email?email=${encodeURIComponent(data.email)}`);
      }
      else if (response.status == HttpStatus.OK && response.data.status == 'fail') {
        toast.error(response.data.message)
      }
    } catch (ex) {
      console.error(ex);
      toast.error("Register failed");
    } finally {
      setIsLoading(false);
    }
  });

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-100 to-indigo-100 px-4">
      <div className="bg-white rounded-xl shadow-xl max-w-md w-full p-8">
        <h2 className="text-3xl font-semibold text-center mb-6 text-gray-900">
          Đăng ký tài khoản
        </h2>

        <Form {...form}>
          {
            <form className="space-y-6" onSubmit={handleRegister} noValidate>
              {/* <FormField
                control={form.control}
                name="username"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="text-gray-700 font-medium">
                      Tên đăng nhập
                    </FormLabel>
                    <FormControl>
                      <Input
                        placeholder="Tên đăng nhập của bạn"
                        {...field}
                        className="focus:ring-indigo-500 focus:border-indigo-500"
                        disabled={isLoading}
                      />
                    </FormControl>
                    <FormMessage className="text-red-600 mt-1" />
                  </FormItem>
                )}
              /> */}

              <FormField
                control={form.control}
                name="email"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="text-gray-700 font-medium">
                      Email
                    </FormLabel>
                    <FormControl>
                      <Input
                        placeholder="Email của bạn"
                        {...field}
                        className="focus:ring-indigo-500 focus:border-indigo-500"
                        disabled={isLoading}
                      />
                    </FormControl>
                    <FormMessage className="text-red-600 mt-1" />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="fullName"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="text-gray-700 font-medium">
                      Họ và tên
                    </FormLabel>
                    <FormControl>
                      <Input
                        placeholder="Họ và tên của bạn"
                        {...field}
                        className="focus:ring-indigo-500 focus:border-indigo-500"
                        disabled={isLoading}
                      />
                    </FormControl>
                    <FormMessage className="text-red-600 mt-1" />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="password"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="text-gray-700 font-medium">
                      Mật khẩu
                    </FormLabel>
                    <FormControl>
                      <Input
                        type="password"
                        placeholder="********"
                        {...field}
                        className="focus:ring-indigo-500 focus:border-indigo-500"
                        disabled={isLoading}
                      />
                    </FormControl>
                    <FormMessage className="text-red-600 mt-1" />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="confirmPassword"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="text-gray-700 font-medium">
                      Nhập lại mật khẩu
                    </FormLabel>
                    <FormControl>
                      <Input
                        type="password"
                        placeholder="********"
                        {...field}
                        className="focus:ring-indigo-500 focus:border-indigo-500"
                        disabled={isLoading}
                      />
                    </FormControl>
                    <FormMessage className="text-red-600 mt-1" />
                  </FormItem>
                )}
              />

              <Button
                type="submit"
                disabled={isLoading}
                className="w-full bg-maincolor text-white font-semibold py-3 rounded-md shadow-md hover:bg-[#005bb5] transition disabled:opacity-50 disabled:cursor-not-allowed"
                style={{ backgroundColor: Color.MainColor }}
              >
                {isLoading ? "Đăng ký..." : "Đăng ký"}
              </Button>

              <div className="flex justify-between text-sm">
                <a
                  href="/"
                  className="inline-flex items-center text-indigo-600 hover:text-indigo-800 transition"
                >
                  <FaArrowLeft className="w-4 h-4 mr-1" />
                  Trở về trang chủ
                </a>
                <a
                  href="/login"
                  className="text-indigo-600 hover:text-indigo-800 transition"
                >
                  Đã có tài khoản? Đăng nhập
                </a>
              </div>
            </form>
          }
        </Form>
      </div>
    </div>
  );
}

export default function Page() {
  return <RegisterForm />;
}
