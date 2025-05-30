"use client";

import React, { useState } from "react";
import styles from "./login.module.css";
import { FcGoogle } from "react-icons/fc";
import { AiFillHome } from "react-icons/ai";

export default function RegisterPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [fullname, setFullname] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const handleRegister = (e: React.FormEvent) => {
    e.preventDefault();
    // TODO: Gọi API login
  };

  return (
    <div className={styles["register-bg"]}>
      <div className={styles["register-card"]}>
        <div className={styles["register-side"]}>
          <img
            src="/AILogin.gif"
            alt="Car animation"
            className={styles["side-img"]}
          />
          <h1 className={styles["welcome-title"]}>Chào mừng bạn!</h1>
          <p className={styles["welcome-desc"]}>
            Đăng ký để sử dụng hệ thống tư vấn luật giao thông.
            <br />
            Chúng tôi luôn sẵn sàng hỗ trợ bạn!
          </p>
        </div>
        <div className={styles["register-container"]}>
          {/* <img src="/bike-rm.png" alt="Logo" className={styles["logo"]} /> */}
          <h2 className={styles["register-title"]}>Đăng ký</h2>
          <form onSubmit={handleRegister} className={styles["register-form"]}>
            <input
              type="fullname"
              placeholder="Ho và tên"
              value={fullname}
              onChange={(e) => setFullname(e.target.value)}
              required
              className={styles["register-input"]}
            />
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className={styles["register-input"]}
            />
            <input
              type="password"
              placeholder="Mật khẩu"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className={styles["register-input"]}
            />
            <input
              type="password"
              placeholder="Xác nhận mật khẩu"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
              className={styles["register-input"]}
            />
            
            <button
              type="submit"
              className={styles["register-button"]}
              style={{ backgroundColor: "#0069d1", color: "#fff" }}
            >
              Đăng ký
            </button>
            <div className={styles["register-divider"]}>
              <span>Hoặc</span>
            </div>
            <button
              type="button"
              className={styles["login-google"]}
              style={{
                backgroundColor: "#f5f5f5",
                color: "#000",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                gap: "10px",
              }}
            >
              <FcGoogle size={22} />
              Đăng nhập với Google
            </button>
            <div className={styles["register-footer"]}>
              <span>Bạn đã có tài khoản? </span>
              <a href="/login" className={styles["register-link"]}>
                Đăng nhập
              </a>
                    </div>
            <div className={styles["register-home"]}>
              <a href="/" className={styles["home-link"]}>
                <AiFillHome size={24} />
              </a>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
