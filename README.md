# app-getmac
Get IP and Mac address


### linux show mac address

```text
cat /sys/class/net/*/address

# VirtualBox
0a:00:27

# Hyper-V
00:15:5d

# VMware
00:50:56
```


### Other Virtual Mac

```text
"00:05:69"; //vmware1
"00:0C:29"; //vmware2
"00:50:56"; //vmware3
"00:1c:14"; //vmware4
"00:1C:42"; //parallels1
"00:03:FF"; //microsoft virtual pc
"00:0F:4B"; //virtual iron 4
"00:16:3E"; //red hat xen , oracle vm , xen source, novell xen
"08:00:27"; //virtualbox
```






### Reference

https://en.wikipedia.org/wiki/MAC_address


https://stackoverflow.com/questions/3062594/differentiate-vmware-network-adapter-from-physical-network-adapters-or-detect?spm=a2c6h.12873639.0.0.2061539f5xHCKp


https://developer.aliyun.com/article/708101


https://github.com/LeMaker/android-actions/blob/6960890188f877ee2cd5d6dbf52b7de25be64085/android/external/chromium_org/chrome/browser/prefs/tracked/pref_hash_calculator_helper_win.cc?spm=a2c6h.12873639.0.0.2061539f5xHCKp&file=pref_hash_calculator_helper_win.cc