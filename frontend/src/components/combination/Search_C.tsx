"use client";
import { Input } from '@/components/modern-ui/input';

export default function Search_C() {
  return (
    <form className="flex gap-2">
      <Input
        type="text"
        placeholder="Nhập từ khóa tìm kiếm..."
        className="flex-1"
      />
      <button
        type="submit"
        className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
      >
        Tìm kiếm
      </button>
    </form>
  );
}