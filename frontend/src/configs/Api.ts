// import Env from "./Env";

export const Api = {
    // BASE_API: Env.backendApiGateWayUrl,
    BASE_API: "http://localhost:8080",

    Authenticaion: {
        LOGIN: '/login',
        REGISTER: '/api/v1/identity/register',
        REFRESH: '/api/v1/identity/refresh',
        USER_INFO: '/api/v1/identity/user'
    },

   User: {
        GET_ALL: '/api/v1/admin/user-management/users',
        DISABLE_USER: '/api/v1/admin/user-management/disable/'
    },



    Law: {
        GET_ALL: '/law/getAll',
        GET_BY_ID: '/law/get/',
        CREATE: '/law/create',   
        UPDATE: '/law/update/',
        DEACTIVATE: '/law/delete/'
    },

    LawType: {
        GET_ALL: '/lawtype/getAll',
        GET_BY_ID: '/api/v1/law/type/get/',
        UPDATE: '/api/v1/law/admin/type/update/',
        CREATE: '/api/v1/law/admin/type/create'
    },

    
    File: {
        UPLOAD: '/api/v1/aws/s3/upload',
        CREATE_FOLDER: '/api/v1/aws/s3/create-folder',
    },

    UsagePackage: {
        CREATE: '/api/v1/user-packages/usage-package/admin/create',
        UPDATE: '/api/v1/user-packages/usage-package/admin/update/',
        DEACTIVATE: '/api/v1/user-packages/usage-package/admin/deactivate/',
        GET_ALL: '/api/v1/user-packages/usage-package/get-all',
        GET_BY_ID: '/api/v1/user-packages/usage-package/get/',
        GET_CURRENT_USAGE_PACKAGE_OF_USER: '/api/v1/user-packages/usage-package/get/current-usage-package/'
    },


    
}