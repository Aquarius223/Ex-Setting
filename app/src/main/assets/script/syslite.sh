#!/system/xbin/sh

#-------------------------------------
# These are final params from Settings
#
#
  SettingXml="/data/user/0/com.miui.purify/purifySetting.xml"
  Enveriment="/data/user/0/com.miui.purify/cache/Enveriment.sh"
  source $Enveriment
#
#
#
#-------------------------------------

#Shell Script Start:
busybox mount -o remount,rw -t auto /system

rm -rf /system/app/AnalyticsCore


reboot
