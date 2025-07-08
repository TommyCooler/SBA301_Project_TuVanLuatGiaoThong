
import type { Metadata } from "next";
import { Montserrat } from "next/font/google";
import "./globals.css";
import { Toaster } from 'sonner';
import { UserProvider } from "@/context/AuthContext";
import { SessionProvider } from "next-auth/react";
import SessionClientProvider from "@/components/SessionClientProvider";

const montserrat = Montserrat({
  variable: "--font-montserrat",
  subsets: ["latin"],
  weight: ["400", "700"],
});


export const metadata: Metadata = {
  title: "Tư Vấn Luật Giao Thông Việt Nam ",
  description: "SWD392 - Tu Van Luat Giao Thong Viet Nam",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body
        className={`${montserrat.variable} antialiased`}
      >
        <SessionClientProvider>
          <UserProvider>
            {children}
          </UserProvider>
        </SessionClientProvider>
        <Toaster />
      </body>
    </html>
  );
}
