#!/system/xbin/sh

#-------------------------------------
# These are final params from Settings
#
#
  Value=$1
  SettingXml="/data/user/0/com.miui.purify/purifySetting.xml"
  Enveriment="/data/user/0/com.miui.purify/cache/Enveriment.sh"
  source $Enveriment
#
#
#
#-------------------------------------

#Shell Script Start:

if [ '1' = $Value ];then
    settings put global policy_control immersive.status=*
else
    settings put global policy_control null
fi
