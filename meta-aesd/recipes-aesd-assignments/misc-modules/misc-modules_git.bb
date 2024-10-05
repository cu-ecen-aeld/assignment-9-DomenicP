LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    git://git@github.com/cu-ecen-aeld/assignment-7-DomenicP.git;protocol=ssh;branch=main \
    file://0001-Only-build-misc-modules.patch \
    file://misc-modules \
    file://module_load \
    file://module_unload \
"

PV = "1.0+git${SRCPV}"
SRCREV = "2b4d19c947db764fa440644e6530054960584e67"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "misc-modules"

FILES:${PN} += "${INIT_D_DIR}/misc-modules"
FILES:${PN} += "${bindir}/module_load"
FILES:${PN} += "${bindir}/module_unload"

do_install:append() {
    install -d "${D}${INIT_D_DIR}"
    install -m 0755 "${WORKDIR}/misc-modules" "${D}${INIT_D_DIR}"

    install -d "${D}${bindir}"
    install -m 0755 "${WORKDIR}/module_load" "${D}${bindir}"
    install -m 0755 "${WORKDIR}/module_unload" "${D}${bindir}"
}
