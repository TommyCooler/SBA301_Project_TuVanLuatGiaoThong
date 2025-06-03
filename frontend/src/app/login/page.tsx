"use client";

import React, { useState } from "react";
import { FcGoogle } from "react-icons/fc";
import { AiFillHome } from "react-icons/ai";

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    // TODO: Gọi API login
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-[#e3f0ff] to-[#f9f9f9] flex items-center justify-center font-sans">
      <div className="flex flex-row bg-white rounded-[18px] shadow-[0_8px_32px_rgba(0,0,0,0.12)] overflow-hidden max-w-[850px] w-full">
        {/* Bên trái */}
        <div className="bg-[#f5faff] w-[340px] flex flex-col items-center justify-center p-8 min-h-[480px]">
          <img
            src="/AILogin.gif"
            alt="Car animation"
            className="w-[120px] mb-4"
          />
          <h1 className="text-[#0069d1] text-xl font-bold mb-2 text-center">
            Chào mừng bạn trở lại!
          </h1>
          <p className="text-gray-800 text-center text-base leading-relaxed">
            Đăng nhập để sử dụng hệ thống tư vấn luật giao thông.
            <br />
            Chúng tôi luôn sẵn sàng hỗ trợ bạn!
          </p>
        </div>

        {/* Bên phải - form */}
        <div className="flex-1 p-10 flex flex-col justify-center">
          <h2 className="text-[#0069d1] text-2xl font-bold mb-6 text-center">
            Đăng nhập
          </h2>
          <form onSubmit={handleLogin} className="flex flex-col gap-4">
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="px-4 py-3 border border-[#d1e3f8] rounded-md text-base focus:outline-none focus:border-[#0069d1] transition"
            />
            <input
              type="password"
              placeholder="Mật khẩu"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="px-4 py-3 border border-[#d1e3f8] rounded-md text-base focus:outline-none focus:border-[#0069d1] transition"
            />
            <div className="text-right text-sm -mt-2">
              <a href="/forgot-password" className="text-[#0069d1] hover:underline">
                Quên mật khẩu?
              </a>
            </div>
            <button
              type="submit"
              className="bg-[#0069d1] text-white rounded-md py-3 font-semibold text-lg hover:bg-[#0051a8] transition shadow-md"
            >
              Đăng nhập
            </button>

            <div className="relative my-2 text-center text-gray-400 text-sm">
              <span className="bg-white px-3 relative z-10">Hoặc</span>
              <div className="absolute top-1/2 left-0 w-full h-px bg-gray-200 -z-10"></div>
            </div>

            <button
              type="button"
              className="bg-gray-100 text-black rounded-md py-3 font-medium flex items-center justify-center gap-2 hover:bg-gray-200 transition"
            >
              <FcGoogle size={22} />
              Đăng nhập với Google
            </button>

            <div className="text-center text-sm mt-2">
              <span>Bạn chưa có tài khoản? </span>
              <a href="/register" className="text-[#0069d1] hover:underline">
                Đăng ký
              </a>
            </div>

            <div className="flex justify-center mt-3">
              <a href="/" className="text-[#0069d1] hover:text-[#003d73]">
                <AiFillHome size={24} />
              </a>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
