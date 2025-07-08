import NextAuth from "next-auth";
import GoogleProvider from "next-auth/providers/google";

const handler = NextAuth({
  providers: [
    GoogleProvider({
      clientId: process.env.GOOGLE_CLIENT_ID!,
      clientSecret: process.env.GOOGLE_CLIENT_SECRET!,
    }),
  ],
  // Optional: callback để custom session nếu muốn
  callbacks: {
    async session({ session, token, user }) {
      // Bạn có thể custom session ở đây nếu cần
      return session;
    },
  },
});

export { handler as GET, handler as POST };
