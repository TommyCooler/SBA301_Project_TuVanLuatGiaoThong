import { useState, useCallback } from "react";
import useAxios from "./useAxios";
import { Api } from "@/configs/Api";
import HttpStatus from "@/configs/HttpStatus";
import { toast } from "sonner";
import axios from "axios"; 

export interface FileUploadResponse {
    fileUrl: string;
    fileName: string;
    folderName: string;
}

export function useFileManager() {
    
    const api = useAxios();
    const [uploadedFile, setUploadedFile] = useState<FileUploadResponse | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    // Upload file to S3
    const uploadFile = useCallback(async (file: File, folderName: string = "laws") => {
        setLoading(true);
        setError(null);
        try {
            const formData = new FormData();
            formData.append('file', file);
            formData.append('folderName', folderName);

            // Sử dụng axios trực tiếp với đường dẫn hardcode
            const response = await axios.post(
                Api.BASE_API + '/api/v1/aws/s3/upload', 
                formData, 
                {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                }
            );

            console.log('Upload response:', response.data);
            
            if (response.status === HttpStatus.CREATED || response.status === HttpStatus.OK) {
                const responseData = response.data.dataResponse;
                console.log('Response data:', responseData);
                
                // Handle different response structures
                let fileData: FileUploadResponse;
                
                if (typeof responseData === 'string') {
                    // If dataResponse is a string (file URL), construct the object
                    fileData = {
                        fileUrl: responseData,
                        fileName: file.name,
                        folderName: folderName
                    };
                } else if (responseData && typeof responseData === 'object' && responseData.fileUrl) {
                    // If dataResponse is already an object with fileUrl
                    fileData = responseData;
                } else {
                    const errorMessage = "Response không có cấu trúc đúng";
                    console.error('Invalid response structure:', responseData);
                    toast.error(errorMessage);
                    throw new Error(errorMessage);
                }
                
                console.log('Processed file data:', fileData);
                setUploadedFile(fileData);
                toast.success("Tải file lên thành công");
                return fileData;
            } else {
                const errorMessage = "Có lỗi xảy ra khi tải file lên";
                toast.error(errorMessage);
                throw new Error(errorMessage);
            }
        } 
        catch (err: any) {
            console.error("Upload error:", err);
            const errorMessage = err.message || "Có lỗi xảy ra khi tải file lên";
            toast.error(errorMessage);
            setError(errorMessage);
            throw err;
        } 
        finally {
            setLoading(false);
        }
    }, []); // Bỏ dependency [api] vì không còn dùng nữa

    // Create folder in S3
    const createFolder = useCallback(async (folderName: string) => {
        setLoading(true);
        setError(null);
        try {
            // Sử dụng axios trực tiếp với đường dẫn hardcode
            const response = await axios.post(
                Api.BASE_API + '/api/v1/aws/s3/create-folder', 
                null, 
                {
                    params: { folderName }
                }
            );

            if (response.status === HttpStatus.CREATED || response.status === HttpStatus.OK) {
                toast.success("Tạo thư mục thành công");
                return response.data.dataResponse;
            } else {
                toast.error("Có lỗi xảy ra khi tạo thư mục");
            }
        } 
        catch (err: any) {
            console.error("Create folder error:", err);
            toast.error("Có lỗi xảy ra khi tạo thư mục");
            setError(err.message || "Unknown error");
            throw err;
        } 
        finally {
            setLoading(false);
        }
    }, []);

    // Clear uploaded file
    const clearUploadedFile = useCallback(() => {
        setUploadedFile(null);
        setError(null);
    }, []);

    return {
        uploadedFile,
        loading,
        error,
        uploadFile,
        createFolder,
        clearUploadedFile,
    };
}
