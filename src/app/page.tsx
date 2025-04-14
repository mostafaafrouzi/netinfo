"use client";

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { useToast } from "@/hooks/use-toast";

export default function Home() {
  const [deviceInfo, setDeviceInfo] = useState({
    name: "",
    version: "",
  });
  const { toast } = useToast();

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
        // Attempt to open Phone Info menu via intent
        const intentUrl = "intent://*#*#4636#*#*/#Intent;scheme=android_secret_code;package=com.android.phone;action=android.intent.action.DIAL;end";
        window.location.href = intentUrl;
      } catch (error) {
        toast({
          title: "Error",
          description: "Failed to open Phone Info menu directly. This may not be supported on your device.",
          variant: "destructive",
        });
      }
    } else {
      toast({
        title: "Error",
        description: "This function is only available on a device.",
        variant: "destructive",
      });
    }
  };

  return (
    <main className="flex flex-col items-center justify-center min-h-screen p-4">
      <h1 className="text-2xl font-bold mb-4">Net Info Access</h1>
      <Button variant="default" size="lg" onClick={openPhoneInfo}>
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
