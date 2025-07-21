-- Insert AI Models
DECLARE @aiModel1 UNIQUEIDENTIFIER = NEWID();
DECLARE @aiModel2 UNIQUEIDENTIFIER = NEWID();  
DECLARE @aiModel3 UNIQUEIDENTIFIER = NEWID();

INSERT INTO ai_models (id, model_name, provider, alias, description, is_deleted, created_date, updated_date) VALUES
(@aiModel1, 'GPT-4', 'OpenAI', 'gpt-4', 'Advanced language model from OpenAI', 0, GETUTCDATE(), GETUTCDATE()),
(@aiModel2, 'GPT-3.5 Turbo', 'OpenAI', 'gpt-3.5-turbo', 'Fast and efficient language model', 0, GETUTCDATE(), GETUTCDATE()),
(@aiModel3, 'Claude-3', 'Anthropic', 'claude-3', 'Anthropic Claude AI model', 0, GETUTCDATE(), GETUTCDATE());

-- Insert Law Types
DECLARE @lawType1 UNIQUEIDENTIFIER = NEWID();
DECLARE @lawType2 UNIQUEIDENTIFIER = NEWID();
DECLARE @lawType3 UNIQUEIDENTIFIER = NEWID();
DECLARE @lawType4 UNIQUEIDENTIFIER = NEWID();

INSERT INTO law_types (id, name, is_deleted, created_date, updated_date) VALUES
(@lawType1, 'Luật Giao thông đường bộ', 0, GETUTCDATE(), GETUTCDATE()),
(@lawType2, 'Nghị định', 0, GETUTCDATE(), GETUTCDATE()),
(@lawType3, 'Thông tư', 0, GETUTCDATE(), GETUTCDATE()),
(@lawType4, 'Quyết định', 0, GETUTCDATE(), GETUTCDATE());

-- Insert Laws
INSERT INTO laws (id, reference_number, dateline, title, law_type_id, issue_date, effective_date, source_url, file_path, is_deleted, created_date, updated_date) VALUES
(NEWID(), '23/2008/QH12', 'Hà Nội', 'Luật Giao thông đường bộ', @lawType1, '2008-11-13', '2009-01-01', 'https://thuvienphapluat.vn/van-ban/Giao-thong-Van-tai/Luat-giao-thong-duong-bo-2008-23-2008-QH12-77648.aspx', '/laws/luat-gt-db-2008.pdf', 0, GETUTCDATE(), GETUTCDATE()),
(NEWID(), '100/2019/NĐ-CP', 'Hà Nội', 'Nghị định về xử phạt vi phạm hành chính trong lĩnh vực giao thông đường bộ', @lawType2, '2019-12-30', '2020-01-01', 'https://thuvienphapluat.vn/van-ban/Giao-thong-Van-tai/Nghi-dinh-100-2019-ND-CP-xu-phat-vi-pham-hanh-chinh-giao-thong-duong-bo-432444.aspx', '/laws/nd-100-2019.pdf', 0, GETUTCDATE(), GETUTCDATE());

-- Insert Usage Packages  
DECLARE @package1 UNIQUEIDENTIFIER = NEWID();
DECLARE @package2 UNIQUEIDENTIFIER = NEWID();
DECLARE @package3 UNIQUEIDENTIFIER = NEWID();

INSERT INTO usage_packages (id, name, description, price, daily_limit, days_limit, is_deleted, created_date, updated_date) VALUES
(@package1, 'Gói Cơ Bản', 'Gói cơ bản dành cho người dùng thông thường với giới hạn 50 câu hỏi/ngày', 99000, 50, 30, 0, GETUTCDATE(), GETUTCDATE()),
(@package2, 'Gói Nâng Cao', 'Gói nâng cao với 200 câu hỏi/ngày và truy cập các model AI tiên tiến', 299000, 200, 30, 0, GETUTCDATE(), GETUTCDATE()),
(@package3, 'Gói Premium', 'Gói không giới hạn cho doanh nghiệp và tổ chức', 999000, 999999, 30, 0, GETUTCDATE(), GETUTCDATE());

-- Insert Users (sử dụng ADMIN, USER, MODERATOR - các giá trị enum hợp lệ)
DECLARE @user1 UNIQUEIDENTIFIER = NEWID();
DECLARE @user2 UNIQUEIDENTIFIER = NEWID(); 
DECLARE @user3 UNIQUEIDENTIFIER = NEWID();

INSERT INTO users (id, username, email, password, fullname, avatar_url, birthday, is_enable, role, created_date, updated_date) VALUES
(@user1, 'user01', 'user01@example.com', '$2a$12$nVHfiLjQJW99MYKR2xDsw.iwtvxEzDvA8iFZHAmpe1Dnny2xkyARe', 'Nguyen Van A', 'https://ui-avatars.com/api/?name=Nguyen+Van+A&background=28a745&color=fff', '1990-05-15', 1, 'USER', GETUTCDATE(), GETUTCDATE()),
(@user2, 'user02', 'user02@example.com', '$2a$12$nVHfiLjQJW99MYKR2xDsw.iwtvxEzDvA8iFZHAmpe1Dnny2xkyARe', 'Tran Thi B', 'https://ui-avatars.com/api/?name=Tran+Thi+B&background=dc3545&color=fff', '1985-08-20', 1, 'USER', GETUTCDATE(), GETUTCDATE()),
(@user3, 'moderator', 'moderator@example.com', '$2a$12$nVHfiLjQJW99MYKR2xDsw.iwtvxEzDvA8iFZHAmpe1Dnny2xkyARe', 'Le Van C', 'https://ui-avatars.com/api/?name=Le+Van+C&background=ffc107&color=000', '1988-12-10', 1, 'USER', GETUTCDATE(), GETUTCDATE());

-- Insert Package-Model relationships
INSERT INTO packages_models (package_id, model_id) VALUES
(@package1, @aiModel2),  -- Gói cơ bản chỉ có GPT-3.5
(@package2, @aiModel2),  -- Gói nâng cao có GPT-3.5 và GPT-4  
(@package2, @aiModel1),
(@package3, @aiModel1),  -- Gói premium có tất cả
(@package3, @aiModel2), 
(@package3, @aiModel3);

-- Insert User Packages
INSERT INTO user_packages (id, order_id, user_id, package_id, price, transaction_date, expired_date, is_enable) VALUES
(NEWID(), 'ORDER-001-' + CAST(NEWID() AS VARCHAR(36)), @user1, @package1, 99000, GETUTCDATE(), DATEADD(day, 30, GETUTCDATE()), 1),
(NEWID(), 'ORDER-002-' + CAST(NEWID() AS VARCHAR(36)), @user2, @package2, 299000, GETUTCDATE(), DATEADD(day, 30, GETUTCDATE()), 1),
(NEWID(), 'ORDER-003-' + CAST(NEWID() AS VARCHAR(36)), @user3, @package3, 999000, GETUTCDATE(), DATEADD(day, 30, GETUTCDATE()), 1);

-- Insert Transaction History
INSERT INTO transaction_history (order_id, user_id, payment_trans_id, pay_type, amount, status, message, paid_at) VALUES
('ORDER-001', @user1, 'PAY-' + CAST(NEWID() AS VARCHAR(36)), 'VNPAY', 99000, 'SUCCESS', 'Thanh toán thành công', GETUTCDATE()),
('ORDER-002', @user2, 'PAY-' + CAST(NEWID() AS VARCHAR(36)), 'MOMO', 299000, 'SUCCESS', 'Thanh toán thành công', GETUTCDATE()),
('ORDER-003', @user3, 'PAY-' + CAST(NEWID() AS VARCHAR(36)), 'VNPAY', 999000, 'SUCCESS', 'Thanh toán thành công', GETUTCDATE());

-- Insert Comments
INSERT INTO comments (id, username, fullname, avatar_url, content, is_anonymous, rating, created_date, updated_date) VALUES
(NEWID(), 'user01', 'Nguyen Van A', 'https://ui-avatars.com/api/?name=Nguyen+Van+A&background=28a745&color=fff', 'Hệ thống rất hữu ích trong việc tra cứu luật giao thông. Giao diện thân thiện và dễ sử dụng.', 0, 5, GETUTCDATE(), GETUTCDATE()),
(NEWID(), 'anonymous', 'Người dùng ẩn danh', NULL, 'AI trả lời khá chính xác về các quy định giao thông. Tuy nhiên cần cải thiện tốc độ phản hồi.', 1, 4, GETUTCDATE(), GETUTCDATE()),
(NEWID(), 'user02', 'Tran Thi B', 'https://ui-avatars.com/api/?name=Tran+Thi+B&background=dc3545&color=fff', 'Cần bổ sung thêm nhiều tài liệu pháp luật và cập nhật các quy định mới nhất.', 0, 3, GETUTCDATE(), GETUTCDATE()),
(NEWID(), 'moderator', 'Le Van C', 'https://ui-avatars.com/api/?name=Le+Van+C&background=ffc107&color=000', 'Tính năng tìm kiếm rất tốt. Hy vọng sẽ có thêm các tính năng mới trong tương lai.', 0, 5, GETUTCDATE(), GETUTCDATE());