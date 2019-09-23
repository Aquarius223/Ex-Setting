#!/system/xbin/sh
SettingXml="/data/user/0/com.miui.purify/purifySetting.xml"
Enveriment="/data/user/0/com.miui.purify/cache/Enveriment.sh"
source $Enveriment

## init shell will be exeuted after apk launcherd

busybox mount -o remount,rw -t auto /system

# ---------------------------

if [ ! -e "/system/etc/purifyboot" ];then
    sed -i '/=recovery/d'  $SettingXml
    touch "/system/etc/purifyboot"
fi

now=`cat /proc/stat|grep btime`
old=`cat /data/user/0/com.miui.purify/stat|grep btime`
if [ "$now" != "$old" ];then
    sed -i '/=reboot/d'  $SettingXml
    echo $now > "/data/user/0/com.miui.purify/stat"
fi

# ---------------------------
