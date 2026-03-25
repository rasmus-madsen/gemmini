package chipyard.config


import chipyard._
import chipyard.harness._
import testchipip._
import testchipip.serdes._
import freechips.rocketchip.subsystem.{ExtIn, FrontBusKey, SlavePortParams}
import org.chipsalliance.cde.config.{Parameters, Config}
//import org.chipsalliance.cde.config.{Config}

// Disable Serial-TL
class WithoutSerialTL extends Config((site, here, up) => {
  case SerialTLKey => None
})


 
class WithSlaveAXI4Port(idBits: Int = 4, sourceBits: Int = 2) extends Config((site, here, up) => {
  case ExtIn => Some(SlavePortParams(
    beatBytes = site(FrontBusKey).beatBytes,
    idBits = idBits,
    sourceBits = sourceBits
  ))
})


//// Add simulation driver for AXI slave
//class WithSimAXISlave extends Config((site, here, up) => {
//  case HarnessBinders => up(HarnessBinders, site) ++ Seq(
//    (system: ChipyardSystem, th: TestHarness, ports: Seq[AXI4Bundle]) => {
//      ports.foreach { axi =>
//        val mem = LazyModule(new SimAXISlave(axi.params))
//        th.connectAXISlave(axi, mem.io)
//      }
//    }
//  )
//})