//    import React from 'react'
//     import Header from '@/components/combination/Header_C'
//     import Footer from '@/components/combination/Footer_C'
//     import ListItems_C from '@/components/items/ListItems_C'
//     import Sibebarlawdetail_C from '@/components/combination/Sibebarlawdetail_C'
//     const lawItems = [
//       {
//         title: "Luật Giao Thông Đường Bộ 2008",
//         description: "Quy định về an toàn giao thông, trách nhiệm của người tham gia giao thông và các quy tắc khi lưu thông trên đường bộ.",
//         image: "/images/law1.jpg",
//       },
//       {
//         title: "Nghị Định 100/2019/NĐ-CP",
//         description: "Quy định xử phạt vi phạm hành chính trong lĩnh vực giao thông đường bộ và đường sắt.",
//         image: "/images/law2.jpg",
//       },
//       {
//         title: "Thông Tư 65/2020/TT-BCA",
//         description: "Quy định nhiệm vụ, quyền hạn, hình thức, nội dung tuần tra, kiểm soát giao thông đường bộ của cảnh sát giao thông.",
//         image: "/images/law3.jpg",
//       },
//     ]

//    export default function Page() {
//   return (
//     <>
//        <Header />
//       <div className="flex min-h-screen bg-gray-50 relative z-0">
//         <Sibebarlawdetail_C />
//         <div className="flex-1 px-4 py-8">
//           {lawItems.map((item, idx) => (
//             <ListItems_C
//               key={idx}
//               title={item.title}
//               description={item.description}
//               image={item.image}
//             />
//           ))}
//         </div>
//       </div>
//       <Footer />
//     </>
//   )
// }
import React from 'react'
import Header from '@/components/combination/Header_C'
import Footer from '@/components/combination/Footer_C'
import Search_C from '@/components/combination/Search_C'
import RightSidebar from '@/components/combination/Rightsidebar_C'
import LawDetail_C from '@/components/combination/Lawdetail_C'
import Link from 'next/link'

// Khi chưa có API, bạn có thể dùng dữ liệu mẫu
const sampleLaw = {
  title: "Chi tiết Luật Giao Thông Đường Bộ 2008",
  content: `<p>Nghị định, Thông tư hướng dẫn Luật Trật tự, an toàn giao thông đường bộ 2024 mới nhất?
Dưới đây là Nghị định, Thông tư hướng dẫn Luật Trật tự, an toàn giao thông đường bộ 2024:

- Nghị định 168/2024/NĐ-CP quy định xử phạt vi phạm hành chính về trật tự, an toàn giao thông trong lĩnh vực giao thông đường bộ; trừ điểm, phục hồi điểm Giấy phép lái xe;

- Nghị định 165/2024/NĐ-CP hướng dẫn Luật Đường bộ 2024 và Điều 77 Luật Trật tự an toàn giao thông đường bộ 2024;

- Nghị định 166/2024/NĐ-CP quy định điều kiện kinh doanh dịch vụ kiểm định xe cơ giới; tổ chức hoạt động của cơ sở đăng kiểm; niên hạn sử dụng của xe cơ giới;

- Nghị định 160/2024/NĐ-CP quy định về hoạt động đào tạo và sát hạch lái xe

- Nghị định 161/2024/NĐ-CP quy định Danh mục hàng hóa nguy hiểm, vận chuyển hàng hóa nguy hiểm và trình tự, thủ tục cấp giấy phép, cấp giấy chứng nhận hoàn thành chương trình tập huấn cho người lái xe hoặc người áp tải vận chuyển hàng hóa nguy hiểm trên đường bộ;

- Nghị định 156/2024/NĐ-CP năm 2024 quy định về đấu giá biển số xe

- Nghị định 151/2024/NĐ-CP hướng dẫn Luật Trật tự, an toàn giao thông đường bộ

- Thông tư 36/2024/TT-BYT quy định về tiêu chuẩn sức khỏe, việc khám sức khỏe đối với người lái xe, người điều khiển xe máy chuyên dùng; việc khám sức khỏe định kỳ đối với người hành nghề lái xe ô tô; cơ sở dữ liệu về sức khỏe của người lái xe, người điều khiển xe máy chuyên dùng

- Thông tư 83/2024/TT-BCA quy định về xây dựng, quản lý, vận hành, khai thác và sử dụng hệ thống giám sát bảo đảm an ninh, trật tự, an toàn giao thông đường bộ

- Thông tư 73/2024/TT-BCA quy định công tác tuần tra, kiểm soát, xử lý vi phạm pháp luật về trật tự, an toàn giao thông đường bộ của Cảnh sát giao thông

- Thông tư 52/2024/TT-BGTVT quy định về yêu cầu kỹ thuật đối với xe cơ giới, xe máy chuyên dùng thuộc đối tượng nghiên cứu phát triển có nhu cầu tham gia giao thông đường bộ

- Thông tư 51/2024/TT-BGTVT về Quy chuẩn kỹ thuật quốc gia về báo hiệu đường bộ

- Thông tư 39/2024/TT-BGTVT quy định về tải trọng, khổ giới hạn của đường bộ; lưu hành xe quá khổ giới hạn, xe quá tải trọng, xe bánh xích trên đường bộ; hàng siêu trường, siêu trọng, vận chuyển hàng siêu trường, siêu trọng; xếp hàng hóa trên phương tiện giao thông đường bộ; cấp giấy phép lưu hành cho xe quá tải trọng, xe quá khổ giới hạn, xe bánh xích, xe vận chuyển hàng siêu trường, siêu trọng trên đường

- Thông tư 79/2024/TT-BCA quy định về cấp, thu hồi chứng nhận đăng ký xe, biển số xe cơ giới, xe máy chuyên dùng

- Thông tư 82/2024/TT-BCA quy định về chứng nhận chất lượng an toàn kỹ thuật và bảo vệ môi trường của xe cơ giới, xe máy chuyên dùng, phụ tùng xe cơ giới trong nhập khẩu, sản xuất, lắp ráp, cải tạo và kiểm định xe cơ giới, xe máy chuyên dùng thuộc phạm vi quản lý của Bộ Công an

- Thông tư 81/2024/TT-BCA Quy chuẩn kỹ thuật quốc gia về biển số xe do Bộ trưởng Bộ Công an ban hành

- Thông tư 35/2024/TT-BGTVT quy định về đào tạo, sát hạch, cấp giấy phép lái xe; cấp, sử dụng giấy phép lái xe quốc tế; đào tạo, kiểm tra, cấp chứng chỉ bồi dưỡng kiến thức pháp luật về giao thông đường bộ

- Thông tư 54/2024/TT-BGTVT quy định về trình tự, thủ tục chứng nhận chất lượng an toàn kỹ thuật và bảo vệ môi trường xe cơ giới, xe máy chuyên dùng, phụ tùng xe cơ giới trong nhập khẩu

- Thông tư 55/2024/TT-BGTVT quy định về trình tự, thủ tục chứng nhận chất lượng an toàn kỹ thuật và bảo vệ môi trường của xe cơ giới, xe máy chuyên dùng, phụ tùng xe cơ giới trong sản xuất, lắp ráp

- Thông tư 46/2024/TT-BGTVT quy định trình tự, thủ tục cấp mới, cấp lại, tạm đình chỉ hoạt động, thu hồi giấy chứng nhận đủ điều kiện hoạt động kiểm định xe cơ giới của cơ sở đăng kiểm xe cơ giới, cơ sở kiểm định khí thải xe mô tô, xe gắn máy

- Thông tư 47/2024/TT-BGTVT quy định trình tự, thủ tục kiểm định, miễn kiểm định lần đầu cho xe cơ giới, xe máy chuyên dùng; trình tự, thủ tục chứng nhận an toàn kỹ thuật và bảo vệ môi trường đối với xe cơ giới cải tạo, xe máy chuyên dùng cải tạo; trình tự, thủ tục kiểm định khí thải xe mô tô, xe gắn máy

- Thông tư 45/2024/TT-BGTVT quy định về cấp mới, cấp lại, tạm đình chỉ, thu hồi chứng chỉ đăng kiểm viên phương tiện giao thông đường bộ

- Thông tư 50/2024/TT-BGTVT về Quy chuẩn kỹ thuật quốc gia cơ sở vật chất kỹ thuật và vị trí cơ sở đăng kiểm xe cơ giới, cơ sở kiểm định khí thải xe mô tô, xe gắn máy và Quy chuẩn kỹ thuật quốc gia về cơ sở bảo hành, bảo dưỡng xe cơ giới

- Thông tư 49/2024/TT-BGTVT về Quy chuẩn kỹ thuật quốc gia Trung tâm sát hạch lái xe cơ giới đường bộ

- Thông tư 53/2024/TT-BGTVT quy định về phân loại phương tiện giao thông đường bộ và dấu hiệu nhận biết xe cơ giới sử dụng năng lượng sạch, năng lượng xanh, thân thiện môi trường

- Thông tư 38/2024/TT-BGTVT về tốc độ và khoảng cách an toàn của xe cơ giới, xe máy chuyên dùng tham gia giao thông trên đường bộ

- Thông tư 72/2024/TT-BCA quy định về Quy trình điều tra, giải quyết tai nạn giao thông đường bộ của Cảnh sát giao thông

- Thông tư 62/2024/TT-BCA Quy chuẩn kỹ thuật quốc gia về “hệ thống giám sát bảo đảm an ninh, trật tự, an toàn giao thông đường bộ”, Quy chuẩn kỹ thuật quốc gia “thiết bị giám sát hành trình và thiết bị ghi nhận hình ảnh người lái xe” và Quy chuẩn kỹ thuật quốc gia “trung tâm chỉ huy giao thông”

- Thông tư 69/2024/TT-BCA quy định về chỉ huy, điều khiển giao thông đường bộ của Cảnh sát giao thông

- Thông tư 65/2024/TT-BCA quy định kiểm tra kiến thức pháp luật về trật tự, an toàn giao thông đường bộ để được phục hồi điểm giấy phép lái xe

- Thông tư 71/2024/TT-BCA quy định quản lý, vận hành, sử dụng hệ thống quản lý dữ liệu thiết bị giám sát hành trình và thiết bị ghi nhận hình ảnh người lái xe

- Thông tư 67/2024/TT-BQP quy định trình tự, thủ tục chứng nhận chất lượng an toàn kỹ thuật và bảo vệ môi trường xe cơ giới nhập khẩu, sản xuất, lắp ráp thuộc phạm vi quản lý của Bộ Quốc phòng

- Thông tư 68/2024/TT-BQP quy định về đào tạo, sát hạch, cấp Giấy phép lái xe quân sự; bồi dưỡng kiến thức pháp luật về giao thông đường bộ cho người điều khiển xe máy chuyên dùng thuộc phạm vi quản lý của Bộ Quốc phòng

- Thông tư 66/2024/TT-BQP quy định về kiểm định an toàn kỹ thuật và bảo vệ môi trường xe cơ giới, xe máy chuyên dùng thuộc quản lý của Bộ Quốc phòng

- Thông tư 69/2024/TT-BQP quy định về đăng ký, quản lý, sử dụng xe cơ giới, xe máy chuyên dùng thuộc phạm vi quản lý của Bộ Quốc phòng

- Thông tư 70/2024/TT-BQP quy định về cải tạo xe cơ giới, xe máy chuyên dùng thuộc phạm vi quản lý của Bộ Quốc phòng.

- Thông tư 71/2024/TT-BQP quy định về kiểm soát quân sự, kiểm tra xe quân sự tham gia giao thông đường bộ

Nghị định, Thông tư hướng dẫn Luật Đường Bộ 2024 mới nhất?
Dưới đây là Nghị định, Thông tư hướng dẫn Luật Đường bộ 2024:

- Nghị định 165/2024/NĐ-CP hướng dẫn Luật Đường bộ 2024 và Điều 77 Luật Trật tự an toàn giao thông đường bộ 2024;

- Nghị định 158/2024/NĐ-CP quy định về hoạt động vận tải đường bộ

- Thông tư 56/2024/TT-BGTVT Quy chuẩn kỹ thuật quốc gia về bến xe khách, Quy chuẩn kỹ thuật quốc gia về bến xe hàng, Quy chuẩn kỹ thuật quốc gia về trạm dừng nghỉ, Quy chuẩn kỹ thuật quốc gia về công trình kiểm soát tải trọng xe trên đường bộ

- Thông tư 40/2024/TT-BGTVT quy định về công tác phòng, chống, khắc phục hậu quả thiên tai trong lĩnh vực đường bộ

- Thông tư 41/2024/TT-BGTVT quy định về quản lý, vận hành, khai thác, bảo trì, bảo vệ kết cấu hạ tầng đường bộ do Bộ trưởng Bộ Giao thông vận tải ban hành

- Thông tư 36/2024/TT-BGTVT quy định về tổ chức, quản lý hoạt động vận tải bằng xe ô tô và hoạt động của bến xe, bãi đỗ xe, trạm dừng nghỉ, điểm dừng xe trên đường bộ; quy định trình tự, thủ tục đưa bến xe, trạm dừng nghỉ vào khai thác

- Thông tư 58/2024/TT-BGTVT quy định về đầu tư điểm dừng xe, đỗ xe và vị trí, quy mô trạm dừng nghỉ, điểm dừng xe, đỗ xe trên đường cao tốc

- Thông tư 34/2024/TT-BGTVT quy định về hoạt động trạm thu phí đường bộ

- Nghị định 130/2024/NĐ-CP quy định về thu phí sử dụng đường bộ cao tốc đối với phương tiện lưu thông trên tuyến đường bộ cao tốc thuộc sở hữu toàn dân do Nhà nước đại diện chủ sở hữu và trực tiếp quản lý, khai thác

- Nghị định 119/2024/NĐ-CP quy định về thanh toán điện tử giao thông đường bộ</p>`,
  createdAt: "01/07/2008",
  tableOfContents: [
    { id: "section1", title: "Nghị định hướng dẫn" },
    { id: "section2", title: "Thông tư hướng dẫn" }
  ]
};

export default function Page() {
  return (
    <div className="flex flex-col min-h-screen bg-gray-50">
      <Header />
      <main className="flex-1 container mx-auto px-4 flex flex-col md:flex-row gap-0 md:gap-8 md:pl-40">
        {/* Cột nội dung chính */}
        <section className="flex-1 max-w-3xl mx-auto md:mx-0 py-8">
          {/* Breadcrumb */}
          <nav className="text-sm text-gray-500 mb-4" aria-label="Breadcrumb">
            <ol className="list-none p-0 inline-flex">
              <li>
                <Link href="/" className="hover:underline text-blue-600">Trang chủ</Link>
                <span className="mx-2">{'>'}</span>
              </li>
              <li>
                <Link href="/law" className="hover:underline text-blue-600">Luật</Link>
                <span className="mx-2">{'>'}</span>
              </li>
              <li className="text-gray-700 font-semibold">Luật giao thông</li>
            </ol>
          </nav>
          <Search_C />
          <LawDetail_C
            title={sampleLaw.title}
            content={sampleLaw.content}
            createdAt={sampleLaw.createdAt}
            tableOfContents={sampleLaw.tableOfContents}
          />
          <div className="mt-10">
            <h2 className="text-lg font-bold mb-4 text-blue-700">Câu hỏi thường gặp</h2>
            <ul className="space-y-3">
              <li>
                <span className="font-semibold">Hỏi:</span> Luật này áp dụng cho đối tượng nào?<br />
                <span className="font-semibold">Đáp:</span> Luật áp dụng cho...
              </li>
              <li>
                <span className="font-semibold">Hỏi:</span> Mức phạt vi phạm là bao nhiêu?<br />
                <span className="font-semibold">Đáp:</span> Mức phạt từ...
              </li>
              {/* ... */}
            </ul>
          </div>
          <div className="mt-10">
            <h2 className="text-lg font-bold mb-4 text-blue-700">Văn bản liên quan</h2>
            <ul className="list-disc pl-5 space-y-1">
              <li>
                <Link href="/law/nd-100-2019" className="text-blue-600 hover:underline">
                  Nghị định 100/2019/NĐ-CP về xử phạt vi phạm giao thông
                </Link>
              </li>
              <li>
                <Link href="/law/tt-65-2020" className="text-blue-600 hover:underline">
                  Thông tư 65/2020/TT-BCA về tuần tra kiểm soát giao thông
                </Link>
              </li>
              {/* ... */}
            </ul>
          </div>
          <div className="mt-10">
            <h2 className="text-lg font-bold mb-4 text-blue-700">Bình luận</h2>
            <textarea className="w-full border rounded p-2 mb-2" rows={3} placeholder="Nhập bình luận của bạn..." />
            <button className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700">Gửi</button>
            {/* Hiển thị danh sách bình luận ở đây */}
          </div>
          <div className="mt-10">
  <h2 className="text-lg font-bold mb-4 text-blue-700">Tài liệu tải về</h2>
  <a href="/files/luat-giao-thong-2008.pdf" className="text-blue-600 hover:underline flex items-center gap-2">
    <svg className="w-5 h-5" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24">
      <path strokeLinecap="round" strokeLinejoin="round" d="M4 16v2a2 2 0 002 2h12a2 2 0 002-2v-2M7 10l5 5 5-5M12 15V3" />
    </svg>
    Tải về Luật Giao Thông Đường Bộ 2008 (PDF)
  </a>
</div>
        </section>
        {/* Cột sidebar bên phải */}
        <div className="hidden md:block w-full md:w-80 mt-20">
          <RightSidebar />
        </div>
      </main>
      <Footer />
    </div>
  )
}