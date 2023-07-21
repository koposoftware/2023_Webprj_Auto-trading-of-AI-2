export const GOOGLE_ID =
  "110307690503-rcdsimgkirrkvbicmnk4kqsho00o6ura.apps.googleusercontent.com";
export const GoogleURL =
  "https://accounts.google.com/o/oauth2/v2/auth?client_id=" +
  GOOGLE_ID +
  "&response_type=token&redirect_uri=https://frontend-beryl-phi.vercel.app/redirect&scope=https://www.googleapis.com/auth/userinfo.email";

export const KAKAO_CLIENT_ID = "4496b2f0ba8560ef8b857078ec76a206";
export const KAKAO_REDIRECT_URI = "https://frontend-beryl-phi.vercel.app/kakao_redirect";
export const KakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_CLIENT_ID}&redirect_uri=${KAKAO_REDIRECT_URI}&response_type=code`;
