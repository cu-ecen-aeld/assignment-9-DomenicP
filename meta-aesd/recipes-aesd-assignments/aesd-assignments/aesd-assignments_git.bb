# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Set this with the path to your assignments repo. Use ssh protocol and see lecture notes
# about how to setup ssh-agent for passwordless access.
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-DomenicP.git;protocol=ssh;branch=main"

PV = "1.0+git${SRCPV}"
# Set to reference a specific commit hash in your assignment repo
SRCREV = "88981a292a6e8a10b421dc35c49a73345ce199e4"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/server"

# TODO: Add the aesdsocket application and any other files you need to install
# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += "${bindir}/aesdsocket"
# Customize these as necessary for any libraries you need for your application
# (and remove comment)
TARGET_CFLAGS += "-std=c11 -Wall -Wextra -pedantic -Wunused -Wconversion"
TARGET_LDFLAGS += "-lrt -pthread"

# Ensure that libgcc_s.so.1 is present in the image. This is required by pthread_exit and should be
# automatic but isn't working for some reason.
# https://docs.yoctoproject.org/pipermail/yocto/2018-March/040227.html
RDEPENDS:${PN} += "libgcc"

inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdsocket-start-stop"

do_install() {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb
	install -d "${D}${bindir}"
	install -m 0755 "${S}/aesdsocket" "${D}${bindir}"

	install -d "${D}${INIT_D_DIR}"
	install -m 0755 "${S}/aesdsocket-start-stop" "${D}${INIT_D_DIR}"
}
