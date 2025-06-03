'use client'
import React from 'react'

export default function RightSidebar() {
  return (
    <aside className="w-full md:w-80 bg-white border border-gray-200 rounded-xl shadow-sm p-4 ml-0 md:ml-8 mt-8 md:mt-0
      md:sticky md:top-24 h-fit">
      <div className="mb-4">
        <div className="bg-gray-100 px-3 py-2 font-bold text-gray-700 rounded-t">THAM CHIẾU ĐẾN NỘI DUNG</div>
        <div className="px-3 py-2 border-b flex items-center gap-2">
          <span className="text-lg">➔</span>
          <span className="font-medium text-gray-800">Văn bản cùng lĩnh vực</span>
        </div>
      </div>
      <div>
        <div className="bg-gray-100 px-3 py-2 font-bold text-gray-700 rounded-t">DANH MỤC CÁC BẢN MIX</div>
        <div className="px-3 py-2 border-b">
          <div className="font-semibold text-black">Timeline</div>
          <div className="text-xs text-gray-500 mb-2">
            Nội dung MIX tại thời điểm có VB mới ban hành tác động đến văn bản đang xem
          </div>
          <div className="font-semibold text-teal-700 mb-2">Văn bản bị tác động thay đổi nội dung</div>
          <div className="mb-3 border-b pb-2">
            <div className="text-[15px] text-yellow-700 font-medium">Nghị định 23/2015/NĐ-CP cấp bản sao từ sổ gốc, chứng thực bản sao từ bản chính</div>
            <div className="text-xs text-gray-600">Ban hành: 16/02/2015</div>
            <div className="text-xs text-gray-600">Hiệu lực: Đã biết</div>
          </div>
          <div>
            <div className="text-[15px] text-yellow-700 font-medium">Nghị định 29/2015/NĐ-CP hướng dẫn Luật Công chứng</div>
            <div className="text-xs text-gray-600">Ban hành: 15/03/2015</div>
            <div className="text-xs text-gray-600">Hiệu lực: Đã biết</div>
          </div>
        </div>
      </div>
    </aside>
  )
}