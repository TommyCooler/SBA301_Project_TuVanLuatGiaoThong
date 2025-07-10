'use client';
import { createContext, useContext, useEffect, useState } from 'react';
import { AuthTokens, User } from '../models/User';
import axios from 'axios';
import { Api } from '@/configs/Api';
import { toast } from 'sonner';
import Constant from '@/configs/Constant';
import { permanentRedirect, useRouter } from 'next/navigation'
import HttpStatus from '@/configs/HttpStatus';


const AUTH_TOKENS_KEY = Constant.AuthTokenKey;
const USER_KEY = 'user';

type UserContextType = {
    user: User | null,
    authTokens: AuthTokens | null,
    //registerUser: (username: string, password: string) => void,
    loginUser: (email: string, password: string) => void,
    logout: () => void,
    isLoggedIn: () => boolean,
    setUser: (user: User) => void,
    setAuthTokens: (authTokens: AuthTokens) => void
}

const UserContext = createContext<UserContextType>({} as UserContextType);


export const UserProvider = ({ children }: { children: React.ReactNode }) => {

    const router = useRouter();
    const [authTokens, setAuthTokens] = useState<AuthTokens | null>(null);
    const [user, setUser] = useState<User | null>(null);
    const [isReady, setIsReady] = useState<boolean>(false);

    useEffect(() => {
        const authTokensString = localStorage.getItem(AUTH_TOKENS_KEY);
        const tokens = authTokensString ? JSON.parse(authTokensString) : null;
        if (tokens) {
            setAuthTokens(tokens);
        }
        // LẤY USER TỪ LOCALSTORAGE
        const userString = localStorage.getItem(USER_KEY);
        const userObj = userString ? JSON.parse(userString) : null;
        if (userObj) {
            setUser(userObj);
        }
        setIsReady(true);
    }, []);


    const loginUser = async (email: string, password: string) => {
        const authenticationRequest = { email, password };
        try {
            const response = await axios.post(Api.BASE_API + Api.Authenticaion.LOGIN, authenticationRequest);
            if (response.status === HttpStatus.OK) {
                setAuthTokens(response.data.dataResponse.token);
                localStorage.setItem(AUTH_TOKENS_KEY, JSON.stringify(response.data.dataResponse.token));
                const userObj: User = {
                    email: response.data.dataResponse.email,
                    role: response.data.dataResponse.role,
                    isEnable: true,
                    fullname: response.data.dataResponse.fullName
                };
                setUser(userObj);
                localStorage.setItem(USER_KEY, JSON.stringify(userObj));
                // Chuyển hướng theo role
                if (userObj.role === "ADMIN") {
                    toast.success("Đăng nhập thành công!");
                    router.push("/admin/dashboard");
                } else {
                    toast.success("Đăng nhập thành công!");
                    router.push("/");
                }
            }
            else if (response.status === HttpStatus.UNAUTHORIZED) {
                toast.error(response.data.message);
            }
        }
        catch (error) {
            console.log(error);
            if (axios.isAxiosError(error) && error.response) {
                toast.error("Đăng nhập thất bại!");
            }
            else {
                toast.error('An unexpected error occurred');
            }
        }
    }


    const logout = () => {
        localStorage.removeItem(AUTH_TOKENS_KEY);
        localStorage.removeItem(USER_KEY);
        setUser(null);
        setAuthTokens(null);
        permanentRedirect(Constant.Page.LoginPage);
    }


    const isLoggedIn = (): boolean => {
        return !!user;
    }

    return (
        <UserContext.Provider value={{
            loginUser, user, authTokens, logout, isLoggedIn, setUser, setAuthTokens
        }}
        >
            {isReady ? children : null}
        </UserContext.Provider>
    )
}

export const useAuth = () => useContext(UserContext);
