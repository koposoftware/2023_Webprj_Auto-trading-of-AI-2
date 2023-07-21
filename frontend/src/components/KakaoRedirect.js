import { useEffect } from "react";
import {
  KAKAO_CLIENT_ID,
  KAKAO_REDIRECT_URI,
} from "../assets/constants/ApiUrls";

const KaKaoRedirect = () => {
  const call = async (grantType, code) => {
    let response = await fetch(
      `https://kauth.kakao.com/oauth/token?grant_type=${grantType}&client_id=${KAKAO_CLIENT_ID}&redirect_uri=${KAKAO_REDIRECT_URI}&code=${code}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
        },
      }
    );
    const res = await response.json();
    const token = res.access_token;

    let response1 = await fetch("/user/login/oauth", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ provider: "kakao", accessToken: token }),
    });

    if (response1.status == 200) {
      response1 = await response1.json();
      localStorage.setItem("token", response1.token);
      window.location.assign("/");
    }
  };
  useEffect(() => {
    const params = new URL(document.location.toString()).searchParams;
    const code = params.get("code");
    const grantType = "authorization_code";
    call(grantType, code);
  }, []);


  return <></>;
};
export default KaKaoRedirect;
