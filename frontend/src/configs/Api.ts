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
        
    },


    Law: {
        GET_ALL: '/law/getAll',
        GET_BY_ID: '/api/v1/law/get/',
        CREATE: '/api/v1/law/admin/create',   
        UPDATE: '/api/v1/law/admin/update/',
        DEACTIVATE: '/api/v1/law/admin/deactivte/'
    },
    
}