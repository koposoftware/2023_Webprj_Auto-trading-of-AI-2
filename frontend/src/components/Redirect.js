import React, { useEffect } from "react";

// 토큰 받은 후 사용자 확인

const Redirect = () => {
  useEffect(() => {
    const t = async () => {
      let hash = window.location.hash;
      let token = hash.split("=")[1].split("&")[0];
      let response = await fetch("https://api-dev.net/user/login/oauth", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ provider: "google", accessToken: token }),
      });
      if (response.status == 200) {
        response = await response.json();
        localStorage.setItem("token", response.token);

        window.location.assign("/");
      }
    };
    t();
  });

  return <></>;
};

export default Redirect;
