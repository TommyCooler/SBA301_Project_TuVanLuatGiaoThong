"use client";

import React, { useState } from "react";
import styles from "./login.module.css";
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
    <div className={styles["login-bg"]}>
      <div className={styles["login-card"]}>
        <div className={styles["login-side"]}>
          <img
            src="/AILogin.gif"
            alt="Car animation"
            className={styles["side-img"]}
          />
          <h1 className={styles["welcome-title"]}>Chào mừng bạn trở lại!</h1>
          <p className={styles["welcome-desc"]}>
            Đăng nhập để sử dụng hệ thống tư vấn luật giao thông.
            <br />
            Chúng tôi luôn sẵn sàng hỗ trợ bạn!
          </p>
        </div>
        <div className={styles["login-container"]}>
          {/* <img src="/bike-rm.png" alt="Logo" className={styles["logo"]} /> */}
          <h2 className={styles["login-title"]}>Đăng nhập</h2>
          <form onSubmit={handleLogin} className={styles["login-form"]}>
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className={styles["login-input"]}
            />
            <input
              type="password"
              placeholder="Mật khẩu"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className={styles["login-input"]}
            />
            <div className={styles["login-forgot"]}>
              <a href="/forgot-password" className={styles["login-link"]}>
                Quên mật khẩu?
              </a>
            </div>
            <button
              type="submit"
              className={styles["login-button"]}
              style={{ backgroundColor: "#0069d1", color: "#fff" }}
            >
              Đăng nhập
            </button>
            <div className={styles["login-divider"]}>
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
            <div className={styles["login-footer"]}>
              <span>Bạn chưa có tài khoản? </span>
              <a href="/register" className={styles["login-link"]}>
                Đăng ký
              </a>
                    </div>
            <div className={styles["login-home"]}>
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
