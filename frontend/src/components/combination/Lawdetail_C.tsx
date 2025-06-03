import React from 'react';

interface LawDetailProps {
  title: string;
  content: string;
  createdAt: string;
  tableOfContents?: {
    id: string;
    title: string;
  }[];
}

export default function LawDetail_C({
  title,
  content,
  createdAt,
  tableOfContents
}: LawDetailProps) {
  return (
    <div className="relative">
      {/* Phần tiêu đề */}
      <h1 className="text-2xl font-bold my-6">{title}</h1>
      
      {/* Ngày tạo (góc phải) */}
      <div className="absolute top-0 right-0 text-sm text-gray-500 bg-gray-100 p-2 rounded">
        Ban hành: {createdAt}
      </div>
      
      {/* Mục lục nổi bật */}
      {tableOfContents && tableOfContents.length > 0 && (
        <div className="border-l-4 border-blue-500 bg-blue-50 rounded-xl p-4 mb-6 shadow flex flex-col gap-2 max-w-xs">
          <div className="flex items-center gap-2 mb-2">
            <svg className="w-5 h-5 text-blue-500" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" d="M4 6h16M4 12h16M4 18h7" />
            </svg>
            <span className="font-bold text-blue-700">Mục lục</span>
          </div>
          <ul className="space-y-1 pl-2">
            {tableOfContents.map((item) => (
              <li key={item.id}>
                <a
                  href={`#${item.id}`}
                  className="text-blue-700 hover:underline"
                >
                  {item.title}
                </a>
              </li>
            ))}
          </ul>
        </div>
      )}
      
      {/* Nội dung chính */}
      <div className="prose max-w-none">
        <div dangerouslySetInnerHTML={{ __html: content }} />
      </div>
    </div>
  );
}