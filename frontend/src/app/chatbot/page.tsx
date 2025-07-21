"use client";

import React, { useState, useEffect, useRef } from "react";
import { IoSend } from "react-icons/io5";
import { FaUser, FaRobot, FaPlus, FaHome, FaBars } from "react-icons/fa";
import { ChatHistory } from "@/models/ChatHistory";
import Link from "next/link";
import { Input } from "@/components/modern-ui/input";
import { Color } from "@/configs/CssConstant";
import {
  Avatar,
  AvatarFallback,
  AvatarImage,
} from "@/components/modern-ui/avatar";
import HeaderTop_C from "@/components/combination/HeaderTop_C";
import { useRouter } from "next/navigation";
import { useAuth } from "@/context/AuthContext";
import useAxios from "@/hooks/useAxios";
import {
  useChatbotManager,
  UserPromptRequest,
} from "@/hooks/useChatbotManager";
import { SAMPLE_QUESTIONS } from "./questions";
import { HiDotsVertical } from "react-icons/hi";
import { FiEdit2, FiTrash2 } from "react-icons/fi";
import { ImSpinner2 } from "react-icons/im";
import { Select } from "@/components/modern-ui/select";

const AUTHENTICATION_REQUIRED = true;
const SHOW_AUTH_TOAST = false;

// Message type for chat UI
interface Message {
  id: number;
  content: string;
  isUser: boolean;
  dateTime?: string;
}

export default function Page() {
  const router = useRouter();
  const { user, isLoggedIn } = useAuth();
  const api = useAxios();
  const {
    chatHistories,
    currentChat,
    loading: chatbotLoading,
    chatHistoriesLoading,
    currentUsagePackage,
    getCurrentUsagePackageOfUser,
    getAllChatHistoriesOfUser,
    askToGenerateWithAuthUser,
    clearCurrentChat,
    renameChatTitle,
    deleteChatHistory,
    addChatHistory,
    setCurrentChat,
  } = useChatbotManager();

  const [selectedChatId, setSelectedChatId] = useState<string>();
  const [selectedSessionId, setSelectedSessionId] = useState<string>();
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [inputMessage, setInputMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [showLoginPrompt, setShowLoginPrompt] = useState(false);
  const [messages, setMessages] = useState<Message[]>([]);
  const [sampleQuestions, setSampleQuestions] = useState<string[]>([]);
  const inputRef = useRef<HTMLInputElement>(null);
  // Dropdown and modal state
  const [openDropdownId, setOpenDropdownId] = useState<string | null>(null);
  const [showRenameModal, setShowRenameModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [renameValue, setRenameValue] = useState("");
  const [targetChatId, setTargetChatId] = useState<string | null>(null);
  const renameInputRef = useRef<HTMLInputElement>(null);
  const [selectedModelAlias, setSelectedModelAlias] =
    useState<string>("gemini-2.0-flash");

  // Set default modelAlias when currentUsagePackage changes or selectedChatId changes
  useEffect(() => {
    if (selectedChatId) {
      // Old chat: lock model to the chat's modelAlias
      const selectedChat = chatHistories.find(
        (chat) => chat.id === selectedChatId
      );
      if (selectedChat && selectedChat.modelAlias) {
        setSelectedModelAlias(selectedChat.modelAlias);
      }
    } else if (
      currentUsagePackage?.aiModels &&
      currentUsagePackage.aiModels.length > 0
    ) {
      // New chat: prefer Gemini 2.0, else first model
      const geminiModel = currentUsagePackage.aiModels.find(
        (m) =>
          m.modelName === "Gemini 2.0" && m.modelAlias === "gemini-2.0-flash"
      );
      if (geminiModel) {
        setSelectedModelAlias(geminiModel.modelAlias);
      } else {
        setSelectedModelAlias(currentUsagePackage.aiModels[0].modelAlias);
      }
    } else if (
      !currentUsagePackage ||
      !currentUsagePackage.aiModels ||
      currentUsagePackage.aiModels.length === 0
    ) {
      setSelectedModelAlias("gemini-2.0-flash");
    }
  }, [currentUsagePackage, selectedChatId, chatHistories]);

  useEffect(() => {
    if (user?.id) {
      getCurrentUsagePackageOfUser(user.id);
      getAllChatHistoriesOfUser(user.id);
    }
  }, [user, getAllChatHistoriesOfUser]);

  useEffect(() => {
    const shuffled = [...SAMPLE_QUESTIONS].sort(() => 0.5 - Math.random());
    setSampleQuestions(shuffled.slice(0, 4));
  }, []);

  // When chatHistories or selectedChatId changes, update messages
  useEffect(() => {
    if (!selectedChatId) {
      setMessages([]);
      return;
    }
    const selectedChat = chatHistories.find(
      (chat) => chat.id === selectedChatId
    );
    if (!selectedChat) {
      setMessages([]);
      return;
    }
    // Convert ChatItem[] to Message[]
    const chatMessages: Message[] = selectedChat.histories.flatMap(
      (item, idx) =>
        [
          item.userText
            ? {
                id: idx * 2,
                content: item.userText,
                isUser: true,
                dateTime: item.createdDate
                  ? typeof item.createdDate === "string"
                    ? item.createdDate
                    : item.createdDate.toISOString()
                  : undefined,
              }
            : undefined,
          item.botText
            ? {
                id: idx * 2 + 1,
                content: item.botText,
                isUser: false,
                dateTime: item.createdDate
                  ? typeof item.createdDate === "string"
                    ? item.createdDate
                    : item.createdDate.toISOString()
                  : undefined,
              }
            : undefined,
        ].filter(Boolean) as Message[]
    );
    setMessages(chatMessages);
  }, [selectedChatId, chatHistories]);

  // When currentChat changes (after sending a message), update messages and chatHistories
  useEffect(() => {
    if (currentChat && selectedChatId === currentChat.id) {
      // Update messages for the current chat
      console.log("Updating messages for current chat:", currentChat.id);
      const chatMessages: Message[] = currentChat.histories.flatMap(
        (item, idx) =>
          [
            item.userText
              ? {
                  id: idx * 2,
                  content: item.userText,
                  isUser: true,
                  dateTime: item.createdDate
                    ? typeof item.createdDate === "string"
                      ? item.createdDate
                      : item.createdDate.toISOString()
                    : undefined,
                }
              : undefined,
            item.botText
              ? {
                  id: idx * 2 + 1,
                  content: item.botText,
                  isUser: false,
                  dateTime: item.createdDate
                    ? typeof item.createdDate === "string"
                      ? item.createdDate
                      : item.createdDate.toISOString()
                    : undefined,
                }
              : undefined,
          ].filter(Boolean) as Message[]
      );
      setMessages(chatMessages);
    }
    // If new chat, select it
    if (currentChat && !selectedChatId) {
      setSelectedChatId(currentChat.id);
      setSelectedChatId(currentChat.sessionId);
    }
  }, [currentChat, selectedChatId]);

  const checkAuthRequired = () => {
    if (!AUTHENTICATION_REQUIRED) {
      return false;
    }
    return !isLoggedIn();
  };

  const handleSendMessage = async () => {
    if (checkAuthRequired()) {
      setShowLoginPrompt(true);
      return;
    }
    if (!inputMessage.trim() || !user?.id) return;
    const userMessage: Message = {
      id: Date.now(),
      content: inputMessage,
      isUser: true,
      dateTime: new Date().toISOString(),
    };
    setMessages((prev) => [...prev, userMessage]);
    setInputMessage("");
    setIsLoading(true);
    try {
      const payload: UserPromptRequest = {
        chatId: selectedChatId || "",
        userId: user.id,
        prompt: inputMessage,
        sessionId: selectedSessionId || "",
        modelAlias:
          selectedModelAlias ||
          (currentUsagePackage?.aiModels?.[0]?.modelAlias ?? ""),
      };
      const result = await askToGenerateWithAuthUser(payload);
      // If this was a new chat, refresh the sidebar
      if (!selectedChatId && result?.id) {
        setSelectedChatId(result.id);
        setSelectedSessionId(result.sessionId);
        addChatHistory(result);
        setCurrentChat(result);
      }
    } catch (error) {
      console.log(error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleChatSelect = (chatId: string, sessionId: string) => {
    setSelectedChatId(chatId);
    setSelectedSessionId(sessionId);
    setIsSidebarOpen(false);
  };

  const getChatTitle = (chat: ChatHistory) => {
    const firstMessage = chat.histories?.[0]?.userText;
    return firstMessage
      ? `${firstMessage.slice(0, 30)}...`
      : "Cuộc trò chuyện mới";
  };

  const handleSuggestedQuestion = (question: string) => {
    setInputMessage(question);
    if (checkAuthRequired()) {
      setShowLoginPrompt(true);
    }
  };

  // New chat handler
  const handleNewChat = () => {
    setSelectedChatId(undefined);
    setSelectedSessionId(undefined);
    setMessages([]);
    setInputMessage("");
    // Clear the current chat to prevent auto-selection
    clearCurrentChat();
    setTimeout(() => {
      inputRef.current?.focus();
    }, 100);
  };

  const handleDropdownOpen = (chatId: string) => {
    setOpenDropdownId(chatId);
  };

  // Open rename modal
  const handleOpenRename = (chatId: string, currentTitle: string) => {
    setTargetChatId(chatId);
    setRenameValue(currentTitle);
    setShowRenameModal(true);
    setOpenDropdownId(null);
    setTimeout(() => renameInputRef.current?.focus(), 100);
  };
  // Open delete modal
  const handleOpenDelete = (chatId: string) => {
    setTargetChatId(chatId);
    setShowDeleteModal(true);
    setOpenDropdownId(null);
  };
  // Confirm rename
  const handleConfirmRename = async () => {
    if (targetChatId && renameValue.trim()) {
      await renameChatTitle(targetChatId, renameValue.trim());
      setShowRenameModal(false);
      setTargetChatId(null);
    }
  };
  // Confirm delete
  const handleConfirmDelete = async () => {
    if (targetChatId) {
      await deleteChatHistory(targetChatId);
      setShowDeleteModal(false);
      setTargetChatId(null);
      // If deleted chat is selected, clear selection
      if (selectedChatId === targetChatId) {
        setSelectedChatId(undefined);
        setSelectedSessionId(undefined);
        setMessages([]);
      }
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 dark:bg-gray-900 transition-colors duration-200">
      {/* Header Section */}
      <div className="sticky top-0 z-40 bg-white dark:bg-gray-800 border-b border-gray-200/50 dark:border-gray-700/50 shadow-sm transition-colors duration-200">
        <div className="max-w-7xl mx-auto px-4">
          <HeaderTop_C logedUser={user} />
        </div>
      </div>

      {/* Main Content */}
      <div className="flex h-[calc(100vh-64px)]">
        {/* Mobile Sidebar Toggle */}
        <button
          onClick={() => setIsSidebarOpen(!isSidebarOpen)}
          className="lg:hidden fixed top-20 left-4 z-50 p-2 rounded-lg bg-white dark:bg-gray-800 shadow-md hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors duration-200"
        >
          <FaBars className="h-6 w-6 text-gray-600 dark:text-gray-300" />
        </button>

        {/* Overlay for mobile */}
        {isSidebarOpen && (
          <div
            className="lg:hidden fixed inset-0 bg-black bg-opacity-50 z-30"
            onClick={() => setIsSidebarOpen(false)}
          />
        )}

        {/* Sidebar */}
        <div
          className={`fixed lg:static w-80 border-r border-gray-200/50 dark:border-gray-700/50 bg-white/95 dark:bg-gray-800/95 backdrop-blur-sm h-full transition-all duration-300 ease-in-out z-30 ${
            isSidebarOpen
              ? "translate-x-0"
              : "-translate-x-full lg:translate-x-0"
          }`}
        >
          <div className="flex flex-col h-full">
            <Link
              href="/"
              className="group flex items-center gap-3 px-4 py-3 text-gray-600 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-700 hover:text-gray-900 dark:hover:text-white transition-all duration-300 rounded-lg mx-2 my-1 hover:shadow-md active:scale-95 border border-gray-200/50 dark:border-gray-700/50 hover:border-gray-300/50 dark:hover:border-gray-600/50 backdrop-blur-sm"
            >
              <div className="flex items-center justify-center w-9 h-9 rounded-full bg-white/80 dark:bg-gray-700/80 group-hover:bg-white dark:group-hover:bg-gray-600 transition-colors duration-300 shadow-sm group-hover:shadow-md border border-gray-200/50 dark:border-gray-700/50 group-hover:border-gray-300/50 dark:group-hover:border-gray-600/50">
                <FaHome
                  className="h-4 w-4 transition-transform duration-300 group-hover:scale-110"
                  style={{ color: Color.MainColor }}
                />
              </div>
              <div className="flex flex-col">
                <span className="font-medium group-hover:text-gray-900 dark:group-hover:text-white transition-colors duration-300">
                  Trở về trang chủ
                </span>
                <span className="text-xs text-gray-400 dark:text-gray-500 group-hover:text-gray-500 dark:group-hover:text-gray-400 transition-colors duration-300">
                  Quay lại màn hình chính
                </span>
              </div>
            </Link>

            <div className="flex h-16 items-center justify-between border-b border-gray-200/50 dark:border-gray-700/50 px-4">
              <h2 className="text-lg font-semibold bg-gradient-to-r from-gray-900 dark:from-white to-gray-600 dark:to-gray-300 bg-clip-text text-transparent">
                Lịch sử chat
              </h2>
              {/* New chat */}
              <button
                title="Tạo cuộc trò chuyện mới"
                onClick={handleNewChat}
                className="rounded-full p-2 hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors duration-200 border border-gray-200/50 dark:border-gray-700/50 hover:border-gray-300/50 dark:hover:border-gray-600/50"
              >
                <FaPlus
                  className="h-5 w-5"
                  style={{ color: Color.MainColor }}
                />
              </button>
            </div>

            <div className="overflow-y-auto flex-1 px-2 py-2">
              {chatHistoriesLoading ? (
                // Loading skeleton for chat histories
                <div className="space-y-2">
                  {[1, 2, 3, 4, 5].map((index) => (
                    <div
                      key={index}
                      className="w-full px-4 py-3 rounded-lg border border-gray-200/50 dark:border-gray-700/50 bg-gray-50/50 dark:bg-gray-700/50 animate-pulse"
                    >
                      <div className="flex flex-col space-y-2">
                        <div className="h-4 bg-gray-200 dark:bg-gray-600 rounded w-3/4"></div>
                        <div className="h-3 bg-gray-200 dark:bg-gray-600 rounded w-1/2"></div>
                      </div>
                    </div>
                  ))}
                </div>
              ) : chatHistories.length > 0 ? (
                chatHistories.map((chat) => (
                  <div key={chat.id} className="relative group">
                    <button
                      onClick={() => {
                        handleChatSelect(chat.id || "", chat.sessionId || "");
                        setIsSidebarOpen(false);
                      }}
                      className={`w-full px-4 py-3 text-left hover:bg-gray-50/80 dark:hover:bg-gray-700/80 transition-all duration-200 rounded-lg mb-1 border border-transparent hover:border-gray-200/50 dark:hover:border-gray-600/50 ${
                        selectedChatId === chat.id
                          ? "bg-gray-50/80 dark:bg-gray-700/80 border-gray-200/50 dark:border-gray-600/50 shadow-sm"
                          : ""
                      } flex items-center justify-between`}
                      style={{ position: "relative" }}
                    >
                      <div className="flex flex-col flex-1 min-w-0">
                        <span className="font-medium text-gray-900 dark:text-gray-100 line-clamp-1">
                          {chat.chatTitle ? chat.chatTitle : getChatTitle(chat)}
                        </span>
                      </div>
                      {/* Dropdown trigger */}
                      <div
                        className="cursor-pointer ml-2 p-1 rounded-full hover:bg-gray-200 dark:hover:bg-gray-600 transition-colors z-10"
                        onClick={(e) => {
                          e.stopPropagation();
                          handleDropdownOpen(chat.id);
                        }}
                        tabIndex={0}
                        aria-label="Chat options"
                        role="button"
                      >
                        <HiDotsVertical className="h-5 w-5 text-gray-500 dark:text-gray-400 group-hover:text-gray-700 dark:group-hover:text-gray-300" />
                      </div>
                      {/* Dropdown menu */}
                      {openDropdownId === chat.id && (
                        <div className="absolute right-2 top-12 w-48 rounded-lg bg-white dark:bg-gray-800 shadow-lg border border-gray-200/50 dark:border-gray-700/50 py-1 z-50 animate-in fade-in zoom-in duration-200">
                          <div
                            className="cursor-pointer w-full text-left px-4 py-2 text-gray-700 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors duration-200 flex items-center gap-2"
                            onClick={() =>
                              handleOpenRename(
                                chat.id,
                                chat.chatTitle || getChatTitle(chat)
                              )
                            }
                            role="button"
                            tabIndex={0}
                          >
                            <FiEdit2 className="h-4 w-4" />
                            Đổi tên
                          </div>
                          <div
                            className="cursor-pointer w-full text-left px-4 py-2 text-red-600 dark:text-red-400 hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors duration-200 flex items-center gap-2"
                            onClick={() => handleOpenDelete(chat.id)}
                            role="button"
                            tabIndex={0}
                          >
                            <FiTrash2 className="h-4 w-4" />
                            Xóa
                          </div>
                        </div>
                      )}
                    </button>
                  </div>
                ))
              ) : (
                // Empty state when no chat histories
                <div className="flex flex-col items-center justify-center py-8 text-center">
                  <div className="w-12 h-12 bg-gray-100 dark:bg-gray-700 rounded-full flex items-center justify-center mb-3">
                    <FaRobot className="h-6 w-6 text-gray-400 dark:text-gray-500" />
                  </div>
                  <p className="text-sm text-gray-500 dark:text-gray-400">
                    Chưa có cuộc trò chuyện nào
                  </p>
                  <p className="text-xs text-gray-400 dark:text-gray-500 mt-1">
                    Bắt đầu chat để tạo lịch sử
                  </p>
                </div>
              )}
            </div>

            {/* User Profile Section */}
            <div className="border-t border-gray-200/50 dark:border-gray-700/50 p-4 bg-gray-50/50 dark:bg-gray-800/50 transition-colors duration-200">
              <div className="flex items-center gap-3">
                <Avatar>
                  <AvatarImage
                    src={
                      user?.avatarUrl ||
                      "https://raw.githubusercontent.com/thangdevalone/modern-ui/refs/heads/main/public/assets/logo.png"
                    }
                    alt="Default avatar"
                  />
                  <AvatarFallback>MD</AvatarFallback>
                </Avatar>
                <div className="flex flex-col">
                  <span className="font-medium text-gray-900 dark:text-white">
                    {user?.fullname}
                  </span>
                  <span className="text-xs text-gray-500 dark:text-gray-400">
                    {}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Main Chat Area */}
        <div className="flex flex-1 flex-col bg-gray-50 dark:bg-gray-900 transition-colors duration-200">
          {/* Chat Container */}
          <div className="flex-1 overflow-y-auto p-4 space-y-4 min-h-0">
            {messages.length > 0 ? (
              messages.map((message) => (
                <div
                  key={message.id}
                  className={`flex items-start gap-3 ${
                    message.isUser ? "justify-end" : "justify-start"
                  }`}
                >
                  {!message.isUser && (
                    <div
                      className="flex-shrink-0 flex h-8 w-8 items-center justify-center rounded-full"
                      style={{ backgroundColor: Color.MainColor }}
                    >
                      <FaRobot className="h-5 w-5 text-white" />
                    </div>
                  )}
                  <div className="flex flex-col min-w-0">
                    <div
                      className={`rounded-lg px-4 py-2 ${
                        message.isUser
                          ? "text-white"
                          : "bg-white dark:bg-gray-800 text-gray-800 dark:text-gray-200 shadow-md border border-gray-200/50 dark:border-gray-700/50"
                      }`}
                      style={
                        message.isUser
                          ? { backgroundColor: Color.MainColor }
                          : {}
                      }
                    >
                      <div className="whitespace-pre-wrap break-words">
                        {message.content}
                      </div>
                    </div>
                    {message.dateTime && (
                      <span className="mt-1 text-xs text-gray-500 dark:text-gray-400">
                        {new Date(message.dateTime).toLocaleTimeString()}
                      </span>
                    )}
                  </div>
                  {message.isUser && (
                    <div className="flex-shrink-0 flex h-8 w-8 items-center justify-center rounded-full bg-gray-500 dark:bg-gray-600">
                      {/* <FaUser className="h-5 w-5 text-white" /> */}
                      <Avatar>
                        <AvatarImage
                          src={
                            user?.avatarUrl ||
                            "https://raw.githubusercontent.com/thangdevalone/modern-ui/refs/heads/main/public/assets/logo.png"
                          }
                          alt="Default avatar"
                        />
                        <AvatarFallback>MD</AvatarFallback>
                      </Avatar>
                    </div>
                  )}
                </div>
              ))
            ) : (
              // Sample questions
              <div className="flex flex-col items-center justify-center h-full">
                <div className="flex flex-col items-center max-w-md text-center">
                  <div className="w-20 h-20 bg-white dark:bg-gray-800 rounded-full shadow-md flex items-center justify-center mb-6 border border-gray-200/50 dark:border-gray-700/50">
                    <FaRobot
                      className="h-10 w-10"
                      style={{ color: Color.MainColor }}
                    />
                  </div>
                  <h3
                    className="text-2xl font-bold mb-3 text-gray-900 dark:text-white"
                    style={{ color: Color.MainColor }}
                  >
                    Chào mừng đến với Tư Vấn Luật Giao Thông
                  </h3>
                  <p className="text-gray-600 dark:text-gray-300 mb-6">
                    Tôi là trợ lý ảo có thể giải đáp các thắc mắc của bạn về
                    luật giao thông đường bộ Việt Nam. Hãy đặt câu hỏi để bắt
                    đầu cuộc trò chuyện!
                  </p>
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-3 w-full">
                    {sampleQuestions.map((question, idx) => (
                      <button
                        key={idx}
                        onClick={() => handleSuggestedQuestion(question)}
                        className="px-4 py-3 bg-gray-100 dark:bg-gray-800 hover:bg-gray-200 dark:hover:bg-gray-700 rounded-lg text-gray-700 dark:text-gray-300 transition-colors text-sm text-left border border-gray-200/50 dark:border-gray-700/50"
                      >
                        {question}
                      </button>
                    ))}
                  </div>
                </div>
              </div>
            )}

            {isLoading && (
              <div className="flex items-start gap-3">
                <div
                  className="flex-shrink-0 flex h-8 w-8 items-center justify-center rounded-full"
                  style={{ backgroundColor: Color.MainColor }}
                >
                  <FaRobot className="h-5 w-5 text-white" />
                </div>
                <div className="flex items-center gap-2 rounded-lg bg-white dark:bg-gray-800 px-4 py-2 shadow-md border border-gray-200/50 dark:border-gray-700/50">
                  <div className="h-2 w-2 animate-bounce rounded-full bg-gray-400 dark:bg-gray-500"></div>
                  <div className="h-2 w-2 animate-bounce rounded-full bg-gray-400 dark:bg-gray-500 delay-100"></div>
                  <div className="h-2 w-2 animate-bounce rounded-full bg-gray-400 dark:bg-gray-500 delay-200"></div>
                </div>
              </div>
            )}
          </div>

          {/* Input Area - Fixed at bottom */}
          <div className="sticky bottom-0 border-t border-gray-200 dark:border-gray-700 bg-white/95 dark:bg-gray-800/95 backdrop-blur-sm p-4 transition-colors duration-200">
            {/* Select model AI at here */}

            <div className="mx-auto flex max-w-4xl items-center gap-2">
              {currentUsagePackage?.aiModels &&
                currentUsagePackage.aiModels.length > 0 && (
                  <Select
                    value={selectedModelAlias}
                    onChange={(e) => setSelectedModelAlias(e.target.value)}
                    className="w-48 dark:bg-gray-700 dark:border-gray-600 dark:text-white"
                    disabled={!!selectedChatId}
                  >
                    {currentUsagePackage.aiModels.map((model) => (
                      <option key={model.modelAlias} value={model.modelAlias}>
                        {model.modelName}
                      </option>
                    ))}
                  </Select>
                )}
              <Input
                type="text"
                value={inputMessage}
                onChange={(e) => setInputMessage(e.target.value)}
                onKeyPress={(e) => e.key === "Enter" && handleSendMessage()}
                placeholder={
                  checkAuthRequired()
                    ? "Đăng nhập để chat với trợ lý..."
                    : "Nhập tin nhắn của bạn..."
                }
                className="flex-1 dark:bg-gray-700 dark:border-gray-600 dark:text-white"
                ref={inputRef}
              />
              <button
                onClick={handleSendMessage}
                disabled={isLoading || !inputMessage.trim()}
                className="flex h-10 w-10 items-center justify-center rounded-lg text-white transition-colors hover:opacity-90 disabled:bg-gray-300 dark:disabled:bg-gray-600"
                style={{ backgroundColor: Color.MainColor }}
              >
                <IoSend className="h-5 w-5" />
              </button>
            </div>
          </div>
        </div>
      </div>

      {/* Login Modal */}
      {showLoginPrompt && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
          <div className="bg-white dark:bg-gray-800 rounded-lg shadow-xl max-w-md w-full p-6 animate-in fade-in zoom-in duration-300">
            <h3 className="text-xl font-bold text-gray-900 dark:text-white mb-4">
              Đăng nhập để tiếp tục
            </h3>
            <p className="text-gray-600 dark:text-gray-300 mb-6">
              Bạn cần đăng nhập để có thể trò chuyện với trợ lý tư vấn luật giao
              thông. Đăng nhập ngay để được hỗ trợ!
            </p>
            <div className="flex justify-end space-x-4">
              <button
                onClick={() => setShowLoginPrompt(false)}
                className="px-4 py-2 text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700 rounded-md transition-colors"
              >
                Ở lại trang
              </button>
              <button
                onClick={() =>
                  router.push("http://localhost:3000/login?returnUrl=/chatbot")
                }
                className="px-4 py-2 text-white rounded-md transition-colors"
                style={{ backgroundColor: Color.MainColor }}
              >
                Đăng nhập ngay
              </button>
            </div>
          </div>
        </div>
      )}
      {/* Rename Modal */}
      {showRenameModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
          <div className="bg-white dark:bg-gray-800 rounded-lg shadow-xl max-w-md w-full p-6 animate-in fade-in zoom-in duration-300">
            <h3 className="text-xl font-bold text-gray-900 dark:text-white mb-4">
              Đổi tên cuộc trò chuyện
            </h3>
            <Input
              ref={renameInputRef}
              value={renameValue}
              onChange={(e) => setRenameValue(e.target.value)}
              className="mb-4 dark:bg-gray-700 dark:border-gray-600 dark:text-white"
              maxLength={50}
              onKeyDown={(e) => {
                if (e.key === "Enter") handleConfirmRename();
              }}
            />
            <div className="flex justify-end space-x-4">
              <button
                onClick={() => setShowRenameModal(false)}
                className="cursor-pointer px-4 py-2 text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700 rounded-md transition-colors"
              >
                Hủy
              </button>
              <button
                onClick={handleConfirmRename}
                className="cursor-pointer px-4 py-2 text-white rounded-md transition-colors flex items-center justify-center gap-2 disabled:opacity-50 disabled:cursor-not-allowed"
                style={{
                  backgroundColor: chatbotLoading ? "#d1d5db" : Color.MainColor,
                }}
                disabled={!renameValue.trim() || chatbotLoading}
              >
                {chatbotLoading ? (
                  <ImSpinner2 className="animate-spin h-4 w-4" />
                ) : null}
                Đổi tên
              </button>
            </div>
          </div>
        </div>
      )}
      {/* Delete Modal */}
      {showDeleteModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
          <div className="bg-white dark:bg-gray-800 rounded-lg shadow-xl max-w-md w-full p-6 animate-in fade-in zoom-in duration-300">
            <h3 className="text-xl font-bold text-gray-900 dark:text-white mb-4">
              Xóa cuộc trò chuyện?
            </h3>
            <p className="text-gray-600 dark:text-gray-300 mb-6">
              Bạn có chắc chắn muốn xóa cuộc trò chuyện này? Hành động này không
              thể hoàn tác.
            </p>
            <div className="flex justify-end space-x-4">
              <button
                onClick={() => setShowDeleteModal(false)}
                className="cursor-pointer px-4 py-2 text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700 rounded-md transition-colors"
              >
                Hủy
              </button>
              <button
                onClick={handleConfirmDelete}
                className="cursor-pointer px-4 py-2 text-white rounded-md transition-colors flex items-center justify-center gap-2 disabled:opacity-50 disabled:cursor-not-allowed"
                style={{
                  backgroundColor: chatbotLoading ? "#d1d5db" : "#ef4444",
                }}
                disabled={chatbotLoading}
              >
                {chatbotLoading ? (
                  <ImSpinner2 className="animate-spin h-4 w-4" />
                ) : null}
                Xóa
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
