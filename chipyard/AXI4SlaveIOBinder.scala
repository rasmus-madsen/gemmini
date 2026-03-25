//package chipyard.iobinders
// 
//import chisel3._
//import org.chipsalliance.cde.config.Parameters
//import freechips.rocketchip.amba.axi4.{AXI4Bundle, AXI4EdgeParameters}
//import freechips.rocketchip.subsystem.{BaseSubsystem, CanHaveSlaveAXI4Port, HasTileLinkLocations, ExtIn, SlavePortParams, FBUS}
//import chipyard.iocell.IOCell._
// 
//case class AXI4SlavePort(getIO: () => AXI4Bundle, params: SlavePortParams, edge: AXI4EdgeParameters)
//  extends Port[AXI4Bundle]
// 
//class WithAXI4SlavePortIO extends OverrideIOBinder({
//  (system: CanHaveSlaveAXI4Port) => {
//    val (ports: Seq[AXI4SlavePort], cells2d) = system.l2_frontend_bus_axi4.zipWithIndex.map({ case (m, i) =>
//      val p = system.asInstanceOf[BaseSubsystem].p
//      val (port, ios) = IOCell.generateIOFromSignal(m, s"axi4_slave_${i}", p, abstractResetAsAsync = true)
//      val edge = system.l2FrontendAXI4Node.edges.out(i)
//      (AXI4SlavePort(() => port, p(ExtIn).get, edge), ios)
//    }).unzip
//    (ports, cells2d.toSeq.flatten)
//  }
//})


package chipyard.iobinders
 
import chisel3._
import org.chipsalliance.cde.config.Parameters
import freechips.rocketchip.amba.axi4.{AXI4Bundle, AXI4EdgeParameters}
import freechips.rocketchip.subsystem.{BaseSubsystem, CanHaveSlaveAXI4Port, HasTileLinkLocations, ExtIn, SlavePortParams, FBUS}
import chipyard.iocell.IOCell
import chipyard.iocell.IOCell._
//import chipyard.IOCellKey
 
case class AXI4SlavePort(getIO: () => AXI4Bundle, params: SlavePortParams, edge: AXI4EdgeParameters)
  extends Port[AXI4Bundle]
 
class WithAXI4SlavePortIO extends OverrideIOBinder({
  (system: CanHaveSlaveAXI4Port) => {
    val (ports: Seq[AXI4SlavePort], cells2d) = system.l2_frontend_bus_axi4.zipWithIndex.map({ case (m, i) =>
      val p = system.asInstanceOf[BaseSubsystem].p
      val (port, ios) = IOCell.generateIOFromSignal(m, s"axi4_slave_${i}", p(IOCellKey), abstractResetAsAsync = true)
      val edge = system.l2FrontendAXI4Node.edges.out(i)
      (AXI4SlavePort(() => port.asInstanceOf[AXI4Bundle], p(ExtIn).get, edge), ios)
    }).unzip
    (ports, cells2d.toSeq.flatten)
  }
})