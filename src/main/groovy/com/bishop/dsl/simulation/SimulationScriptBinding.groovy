package com.bishop.dsl.simulation

class SimulationScriptBinding extends Binding {

    def altitude = 0
    def sv01 = 0
    def sv02 = 0
    def sv03 = 0

    public Object getVariable(String name) {
        if (hasVariable(name + "Malf")) {
            Malfunction malf = (Malfunction) super.getVariable(name + "Malf")
            if (malf != null && malf.active) {
                return super.getVariable(name) + malf.offset
            }
            return super.getVariable(name)
        }
        return super.getVariable(name)
    }

    public void setVariable(String name, Object value) {
        switch (name) {
            case "sv01":
                sv01 = value
                super.setVariable("sv01", sv01)
                break
            case "sv02":
                sv02 = value
                super.setVariable("sv02", sv02)
                break
            case "sv03":
                sv03 = value
                super.setVariable("sv03", sv03)
                break
        }
    }

    public void initialize() {
        super.setVariable("altitude", altitude)
        super.setVariable("sv01", sv01)
        super.setVariable("sv02", sv02)
        super.setVariable("sv03", sv03)
        super.setVariable("sv01Malf", new Malfunction())
        super.setVariable("sv02Malf", new Malfunction())
        super.setVariable("sv03Malf", new Malfunction())
    }

    public void update() {
        altitude += 1000
        sv01++
        sv02++
        sv03++
        super.setVariable("altitude", altitude)
        super.setVariable("sv01", sv01)
        super.setVariable("sv02", sv02)
        super.setVariable("sv03", sv03)
    }

}
