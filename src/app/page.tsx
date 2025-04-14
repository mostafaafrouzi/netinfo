"use client";

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";

export default function Home() {
  const [deviceInfo, setDeviceInfo] = useState({
    name: "",
    version: "",
  });

  useEffect(() => {
    // Access navigator only on the client side
    if (typeof window !== "undefined") {
      setDeviceInfo({
        name: navigator.userAgentData?.brands[0]?.brand || "Unknown Device",
        version: navigator.userAgentData?.platform || "Unknown Version",
      });
    }
  }, []);

  const openPhoneInfo = async () => {
    if (typeof window !== "undefined") {
      try {
        // Using an intent to open the Phone Info menu (may not work on all devices)
        window.location.href = "intent://*#*#4636#*#*/#Intent;scheme=android_secret_code;package=com.android.phone;action=android.intent.action.DIAL;end";
      } catch (error) {
        alert("Failed to open Phone Info menu directly. Please check compatibility.");
      }
    } else {
      alert("This function is only available on a device.");
    }
  };

  return (
    <main className="flex flex-col items-center justify-center min-h-screen p-4">
      <h1 className="text-2xl font-bold mb-4">Net Info Access</h1>
      <Button variant="default" size="lg" className="mb-4 bg-teal-500 hover:bg-teal-700 text-white font-bold py-2 px-4 rounded" onClick={openPhoneInfo}>
        Open Phone Info
      </Button>
      {deviceInfo.name && deviceInfo.version && (
        <div className="absolute bottom-4 left-4 text-sm">
          Device: {deviceInfo.name} - Android: {deviceInfo.version}
        </div>
      )}
    </main>
  );
}
