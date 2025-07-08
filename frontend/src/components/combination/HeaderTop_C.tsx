'use client';
import { HiCheck, HiChevronDown } from "react-icons/hi";
import { FiGlobe, FiUser, FiSettings, FiLogOut } from "react-icons/fi";
import { Color } from "@/configs/CssConstant";
import Constant from "@/configs/Constant";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/modern-ui/avatar";
import { useState, useRef, useEffect } from "react";
import GavelIcon from '@mui/icons-material/Gavel';
import PeopleIcon from '@mui/icons-material/People';
import { useAuth } from "@/context/AuthContext";
import { useSession, signOut } from "next-auth/react";

// type HeaderTop_CProps = {
//     logedUser?: User;
// }

export default function HeaderTop_C() {
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const dropdownRef = useRef<HTMLDivElement>(null);
    const { user, logout } = useAuth();
    const { data: session } = useSession();

    // Lấy tên ưu tiên: user.fullname (login thường) > session.user.name (Google) > "User"
    const displayName = user?.fullname || session?.user?.name || "User";
    const avatarUrl = user?.avatarUrl || session?.user?.image || "https://raw.githubusercontent.com/thangdevalone/modern-ui/refs/heads/main/public/assets/logo.png";
    const isAdmin = user?.role === "ADMIN";

    // Close dropdown when clicking outside
    useEffect(() => {
        function handleClickOutside(event: MouseEvent) {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
                setIsDropdownOpen(false);
            }
        }

        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, []);

    // Xác định đã đăng nhập: login thường hoặc Google
    const isLoggedIn = !!user || !!session?.user;

    return (
        <div className="sticky top-0 z-50 bg-white border-b border-gray-200/50 shadow-sm">
            <div className="max-w-7xl mx-auto px-4">
                {/* Top bar */}
                <div className="flex justify-between items-center py-2 text-sm font-normal text-gray-800">
                    {/* Left: Logo */}
                    <div className="flex items-center space-x-3">
                        {/* Icon */}
                        <div className="w-8 h-8 flex items-center justify-center rounded-full border-2 border-blue-600 text-blue-600">
                            <HiCheck size={20} />
                        </div>
                        <span
                            className="text-2xl font-semibold select-none cursor-default"
                            style={{ color: Color.MainColor }}
                        >
                            Tư Vấn Luật Giao Thông Việt Nam
                        </span>
                    </div>

                    {/* Right top menu */}
                    <nav className="flex items-center space-x-6 text-sm">
                        <a href="#" className="hover-maincolor transition">Giới thiệu</a>
                        <a href="#" className="hover-maincolor transition">Cộng đồng</a>

                        {/* Region with globe icon */}
                        <button className="flex items-center space-x-1 hover-maincolor transition">
                            <FiGlobe size={20} />
                            <span>Ngôn ngữ</span>
                        </button>

                        {/* User section */}
                        {isLoggedIn ? (
                            <div className="relative" ref={dropdownRef}>
                                <button
                                    onClick={() => setIsDropdownOpen(!isDropdownOpen)}
                                    className="flex items-center space-x-2 hover:bg-gray-50 p-2 rounded-lg transition-colors duration-200"
                                >
                                    <Avatar className="h-8 w-8">
                                        <AvatarImage src={avatarUrl} alt="User avatar" />
                                        <AvatarFallback>{displayName.charAt(0) || 'U'}</AvatarFallback>
                                    </Avatar>
                                    <span className="font-medium">{displayName}</span>
                                    <HiChevronDown className={`h-5 w-5 transition-transform duration-200 ${isDropdownOpen ? 'rotate-180' : ''}`} />
                                </button>

                                {/* Dropdown Menu */}
                                {isDropdownOpen && (
                                    <div className="absolute right-0 mt-2 w-48 rounded-lg bg-white shadow-lg border border-gray-200/50 py-1 z-50">
                                        <a
                                            href="/settings"
                                            className="flex items-center space-x-2 px-4 py-2 text-gray-700 hover:bg-gray-50 transition-colors duration-200"
                                        >
                                            <FiSettings className="h-5 w-5" />
                                            <span>Cài đặt</span>
                                        </a>
                                        <hr className="my-1 border-t border-gray-100" />
                                        <button
                                            onClick={() => {
                                                if (session?.user) {
                                                    signOut({ callbackUrl: "/" });
                                                } else {
                                                    logout();
                                                }
                                            }}
                                            className="w-full flex items-center space-x-2 px-4 py-2 text-red-700 hover:bg-gray-50 transition-colors duration-200"
                                        >
                                            <FiLogOut className="h-5 w-5" />
                                            <span>Đăng xuất</span>
                                        </button>
                                        {/* Nếu là ADMIN thì hiện thêm các mục bên dưới */}
                                        {isAdmin && (
                                            <>
                                                <a
                                                    href="/admin/law-management"
                                                    className="flex items-center space-x-2 px-4 py-2 text-gray-700 hover:bg-gray-50 transition-colors duration-200"
                                                >
                                                    <GavelIcon />
                                                    <span>Luật Hiện Hành</span>
                                                </a>
                                                <hr className="my-1 border-t border-gray-100" />
                                                <a
                                                    href="/admin/user-management"
                                                    className="flex items-center space-x-2 px-4 py-2 text-gray-700 hover:bg-gray-50 transition-colors duration-200"
                                                >
                                                    <PeopleIcon />
                                                    <span>Quản lý người dùng</span>
                                                </a>
                                            </>
                                        )}
                                    </div>
                                )}
                            </div>
                        ) : (
                            <button className="flex items-center space-x-1 hover-maincolor transition">
                                <FiUser size={20} />
                                <a href={Constant.Page.LoginPage}>Đăng nhập/Đăng ký</a>
                            </button>
                        )}
                    </nav>
                </div>
            </div>
        </div>
    )
}
