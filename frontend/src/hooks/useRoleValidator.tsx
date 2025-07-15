import { useMemo } from "react";
import { Role, User } from "@/models/User";
import { decodeHashedString } from "@/ownUtils/all/hashingUtil";

interface RoleValidator {
  isUser: boolean;
  isAdmin: boolean;
  role: string | null;
  hasRole: (targetRole: string) => boolean;
}

export function useRoleValidator(user: User | null | undefined): RoleValidator {
  return useMemo(() => {
    if (!user?.role) {
      return {
        isUser: false,
        isAdmin: false,
        role: null,
        hasRole: () => false,
      };
    }

    let decodedRole: string;
    try {
      decodedRole = decodeHashedString(user.role);
      console.log("Decoded role:", decodedRole);
    } 
    catch (error) {
      decodedRole = user.role;
      console.error("Error decoding role:", error);
      console.log("Error Decoded role:", decodedRole);
    }

    const result = {
      isUser: decodedRole === Role.USER || decodedRole === "ROLE_USER",
      isAdmin: decodedRole === Role.ADMIN || decodedRole === "ROLE_ADMIN",
      role: decodedRole,
      hasRole: (targetRole: string) => decodedRole === targetRole,
    };
    return result;
  }, [user?.role]);
}
