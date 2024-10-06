LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-DomenicP.git;protocol=ssh;branch=main"

PV = "1.0+git${SRCPV}"
SRCREV = "10442052329010157474c36f46d022037c3b85b8"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar"

FILES:${PN} += "${INIT_D_DIR}/aesdchar"
FILES:${PN} += "${bindir}/aesdchar_load"
FILES:${PN} += "${bindir}/aesdchar_unload"

do_install:append() {
    install -d "${D}${INIT_D_DIR}"
    install -m 0755 "${S}/aesdchar" "${D}${INIT_D_DIR}"

    install -d "${D}${bindir}"
    install -m 0755 "${S}/aesdchar_load" "${D}${bindir}"
    install -m 0755 "${S}/aesdchar_unload" "${D}${bindir}"
}
