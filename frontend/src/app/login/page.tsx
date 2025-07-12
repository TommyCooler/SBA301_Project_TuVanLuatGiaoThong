// "use client";

// import React, { useState } from "react";
// import { useAuth } from "@/context/AuthContext";
// import { useRouter } from "next/navigation";
// import { FcGoogle } from "react-icons/fc";
// import { AiFillHome } from "react-icons/ai";
// import { signIn } from "next-auth/react";
// import { useSession } from "next-auth/react";
// import { useEffect } from "react";

// export default function LoginPage() {
//   const [email, setEmail] = useState("");
//   const [password, setPassword] = useState("");
//   const { loginUser } = useAuth();
//   const router = useRouter();
//   const { data: session, status } = useSession();

//    // Tự động redirect nếu đã đăng nhập bằng Google hoặc next-auth
//    useEffect(() => {
//     if (status === "authenticated") {
//       router.push("/"); // hoặc trang bạn muốn
//     }
//   }, [status, router]);

//   const handleLogin = async (e: React.FormEvent) => {
//     e.preventDefault();
//     await loginUser(email, password);
   
//   };

//   return (
//     <div className="min-h-screen bg-gradient-to-br from-[#e3f0ff] to-[#f9f9f9] flex items-center justify-center font-sans">
//       <div className="flex flex-row bg-white rounded-[18px] shadow-[0_8px_32px_rgba(0,0,0,0.12)] overflow-hidden max-w-[850px] w-full">
//         {/* Bên trái */}
//         <div className="bg-[#f5faff] w-[340px] flex flex-col items-center justify-center p-8 min-h-[480px]">
//           <img
//             src="/AILogin.gif"
//             alt="Car animation"
//             className="w-[120px] mb-4"
//           />
//           <h1 className="text-[#0069d1] text-xl font-bold mb-2 text-center">
//             Chào mừng bạn trở lại!
//           </h1>
//           <p className="text-gray-800 text-center text-base leading-relaxed">
//             Đăng nhập để sử dụng hệ thống tư vấn luật giao thông.
//             <br />
//             Chúng tôi luôn sẵn sàng hỗ trợ bạn!
//           </p>
//         </div>

//         {/* Bên phải - form */}
//         <div className="flex-1 p-10 flex flex-col justify-center">
//           <h2 className="text-[#0069d1] text-2xl font-bold mb-6 text-center">
//             Đăng nhập
//           </h2>
//           <form onSubmit={handleLogin} className="flex flex-col gap-4">
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
//             <div className="text-right text-sm -mt-2">
//               <a href="/forgot-password" className="text-[#0069d1] hover:underline">
//                 Quên mật khẩu?
//               </a>
//             </div>
//             <button
//               type="submit"
//               className="bg-[#0069d1] text-white rounded-md py-3 font-semibold text-lg hover:bg-[#0051a8] transition shadow-md"
//             >
//               Đăng nhập
//             </button>

//             <div className="relative my-2 text-center text-gray-400 text-sm">
//               <span className="bg-white px-3 relative z-10">Hoặc</span>
//               <div className="absolute top-1/2 left-0 w-full h-px bg-gray-200 -z-10"></div>
//             </div>

//             <button
//               type="button"
//               className="bg-gray-100 text-black rounded-md py-3 font-medium flex items-center justify-center gap-2 hover:bg-gray-200 transition"
//               onClick={() => signIn("google")}
//             >
//               <FcGoogle size={22} />
//               Đăng nhập với Google
//             </button>

//             <div className="text-center text-sm mt-2">
//               <span>Bạn chưa có tài khoản? </span>
//               <a href="/register" className="text-[#0069d1] hover:underline">
//                 Đăng ký
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
import Env from "@/configs/Env";
import { useAuth } from "@/context/AuthContext";
import { toast } from "sonner";
import { Color } from "@/configs/CssConstant";
import { FaArrowLeft } from "react-icons/fa";

const formSchema = z.object({
  username: z.string().min(3, {
    message: "Tên người dùng phải có ít nhất 3 ký tự.",
  }),
  password: z.string().min(1, {
    message: "Mật khẩu phải có ít nhất 8 ký tự.",
  }),
});

function LoginForm() {
  const form = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      username: "",
      password: "",
    },
  });

  const { loginUser } = useAuth();
  const [isLoading, setIsLoading] = useState(false);

  // Use form.handleSubmit for proper validation and submission
  const handleLogin = form.handleSubmit(async (data) => {
    if (!data.username) {
      toast.error("Tên đăng nhập không được để trống!");
      return;
    }
    if (!data.password) {
      toast.error("Mật khẩu không được để trống");
      return;
    }

    try {
      setIsLoading(true);
      await loginUser(data.username, data.password);
    } catch (ex) {
      console.error(ex);
      toast.error("Login failed");
    } finally {
      setIsLoading(false);
    }
  });

  const handleLoginWithGoogle = () => {
    window.location.href = Env.backendOAuth2Url?.toString() || "";
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-100 to-indigo-100 px-4">
      <div className="bg-white rounded-xl shadow-xl max-w-md w-full p-8">
        <h2 className="text-3xl font-semibold text-center mb-6 text-gray-900">
          Đăng nhập
        </h2>

        <Form {...form}>
          <form className="space-y-6" onSubmit={handleLogin} noValidate>
            <FormField
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

            <div className="flex justify-end text-sm">
              <a href="/register"
                className="text-indigo-600 hover:text-indigo-800 transition">
                Chưa có tài khoản? Đăng ký
              </a>
            </div>

            <Button
              type="submit"
              disabled={isLoading}
              className="w-full bg-maincolor text-white font-semibold py-3 rounded-md shadow-md hover:bg-[#005bb5] transition disabled:opacity-50 disabled:cursor-not-allowed"
              style={{ backgroundColor: Color.MainColor }}
            >
              {isLoading ? "Đăng nhập..." : "Đăng nhập"}
            </Button>

            {/* Sign in with Google Button */}
            <Button
              type="button"
              onClick={handleLoginWithGoogle}
              variant="outline"
              className="w-full flex items-center justify-center gap-3 border border-gray-300 hover:bg-gray-100 transition mt-3 py-3 rounded-md font-semibold"
              disabled={isLoading}
            >
              {/* Google Logo SVG */}
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 533.5 544.3"
                className="w-5 h-5"
                fill="none"
              >
                <path
                  d="M533.5 278.4c0-18.6-1.5-37.5-4.7-55.4H272.1v104.9h147.5c-6.4 34.4-26.4 63.5-56.5 83v68h91.5c53.5-49.2 84.9-121.8 84.9-200.5z"
                  fill="#4285F4"
                />
                <path
                  d="M272.1 544.3c76.7 0 141.2-25.4 188.2-68.8l-91.5-68c-25.4 17-58 27.2-96.7 27.2-74.3 0-137.4-50.1-159.8-117.4h-94v73.8c46.6 92.5 142.6 153.2 253.8 153.2z"
                  fill="#34A853"
                />
                <path
                  d="M112.3 322.3c-11.5-34.2-11.5-70.7 0-104.9v-73.8h-94c-38.7 75.3-38.7 164.6 0 239.9l94-61.2z"
                  fill="#FBBC05"
                />
                <path
                  d="M272.1 107.7c40.6-.6 79.8 14.5 109.3 42.9l82-82.3C405 24.9 342 0 272.1 0 160.9 0 64.9 60.7 18.3 153.2l94 73.8c22.4-67.3 85.5-117.4 159.8-119.3z"
                  fill="#EA4335"
                />
              </svg>
              Đăng nhập với Google
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
                href="#"
                className="text-indigo-600 hover:text-indigo-800 transition"
              >
                Quên mật khẩu?
              </a>
            </div>
          </form>
        </Form>
      </div>
    </div>
  );
}

export default function Page() {
  return <LoginForm />;
}
